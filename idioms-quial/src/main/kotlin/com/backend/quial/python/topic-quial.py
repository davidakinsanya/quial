import undetected_chromedriver as uc
from selenium.webdriver.common.by import By
from selenium.common import exceptions
import pandas as pd
import chromedriver_autoinstaller as cd

import string
import re
import csv
import os, os.path
from cleantext import clean
import random

url = "https://www.theidioms.com/"

idiom_dict = {}
    

def main_driver():
    options = uc.ChromeOptions()
    options.add_argument('--headless=new')
    options.add_argument("--window-size=1920,1080")
    options.add_argument('--ignore-certificate-errors')
    options.add_argument('--allow-running-insecure-content')
    options.add_argument("--disable-extensions")
    options.add_argument("--proxy-server='direct://'")
    options.add_argument("--proxy-bypass-list=*")
    options.add_argument("--start-maximized")
    options.add_argument('--disable-gpu')
    options.add_argument('--disable-dev-shm-usage')
    options.add_argument('--no-sandbox')

    return uc.Chrome(options=options,
                     driver_executable_path = cd.install())




def alt_meanings(idiom, idiom_list):
    idiom_breakdown = idiom.split()
    alt_list = []

    for i in range (0, len(idiom_list)):
        main_string = idiom_list[i]
        for word in idiom_breakdown:
            if word in main_string:
                alt_list.append(main_string.replace('.', ' '))
            break
                
    return alt_list[0].split('\n')
    

def get_additional_meaning(idiom, link):
    driver.get(link)

    ul_elems = driver.find_elements(By.CSS_SELECTOR, 'ul')
    ol_elems = driver.find_elements(By.CSS_SELECTOR, 'ol')

    
    ul_elems_comp = [elem.text for elem in ul_elems]
    ol_elems_comp = [elem.text for elem in ol_elems]


    meanings_list1 = ol_elems_comp[1].split('\n')

    final_meanings = []

    if ('(64)' in meanings_list1[0]):
        final_meanings = alt_meanings(idiom, ul_elems_comp)
    else:
        final_meanings = ol_elems_comp[1].split('\n')

           
    return [ul_elems_comp[1].split('\n'), final_meanings]


    
def get_meaning(idiom):
    idiom_arr = idiom.split('\n')
    idiom_arr[1] = re.sub(r"\(.*?\)","()" ,idiom_arr[1]) # removes everything inside ()
    idiom_arr[1] = idiom_arr[1].replace("()", "").strip().replace(" Read more ➺", "") # removes () themselves
    if (len(idiom_arr) == 3):
        idiom_arr[2] = idiom_arr[2].replace(" Read more ➺", "")
        
    return idiom_arr


def scrape_topic():
    driver.get(url + "topics/")

    topics_list = []
    
    idioms_links = driver.find_elements(By.CSS_SELECTOR, "ul.wp-tag-cloud")
    topics = idioms_links[0].find_elements(By.CSS_SELECTOR,'a')
    topics += idioms_links[1].find_elements(By.CSS_SELECTOR,'a')

    for i in range(0, len(topics)):
        if (topics[i].text != "Crush"):
            topics_list.append([topics[i].text, topics[i].get_attribute('href')])
    return topics_list



def get_links(idioms):
    arr = []
    for i in range(0, len(idioms)):
        arr.append(idioms[i].find_elements(By.CSS_SELECTOR, "a")[0].get_attribute('href'))
    return arr


def get_data(topic, idioms, links, alt):
    idioms_list = [text.text for text in idioms]

    try:
        edge_case = idioms[0].find_elements(By.CSS_SELECTOR, "div.idiom")
    
        for i in range(1, len(idioms_list)):
            basic_info = get_meaning(idioms_list[i])
            meanings_list = get_additional_meaning(idioms_list[i].split('\n')[0], links[i])

            if (len(basic_info) == 2):
                meanings_list = get_additional_meaning(idioms_list[i].split('\n')[0], alt[i])

                if "Meaning: " not in basic_info[1]:
                     replacement_meaning = "Meaning: " + meanings_list[0][0]
                     basic_info.insert(1, replacement_meaning)
                elif "Example: " not in basic_info[1]:
                    replacement_example = "Example: " + meanings_list[1][0]
                    basic_info.insert(2, replacement_example)
                    
            
            idiom_dict[topic][0] += [basic_info]
            idiom_dict[topic][1] += [meanings_list[0]]
            idiom_dict[topic][2] += [meanings_list[1]]
                
    except TypeError:
        pass
    

def main_scrape(topic, url, page):
    curr_page = 1
    max_page = 1
    
    if page == 1:
        idiom_dict[topic] = [[], [], []]
        
    driver.get(url + topic + "/page/" + str(page))

    page_info = driver.find_elements(By.CSS_SELECTOR, 'p.pno')
    
    if len(page_info) != 0:
        curr_page = page_info[0].text.split()[1]
        max_page = page_info[0].text.split()[-1]
        random_page = random.randint(int(curr_page), int(max_page)-1)
        driver.get(url + topic + "/page/" + str(random_page))

    idioms = driver.find_elements(By.CSS_SELECTOR, "div.idiom")

    alt_links = driver.find_elements(By.CSS_SELECTOR, "p")
    alt_links_list = [link.find_elements(By.CSS_SELECTOR, "a") for link in alt_links]
    alt_links_list2 = []
        
        
    for i in range(0, len(alt_links_list) -1):
        if len(alt_links_list[i]) == 1:
            alt_link = alt_links_list[i][0].get_attribute('href')
            
            if alt_link not in alt_links_list2:
                alt_links_list2.append(alt_link)

    
    

    idioms_list = [text.text for text in idioms]
    links_list = get_links(idioms)
    get_data(topic, idioms, links_list, alt_links_list2)
    
    if page_info != 0 and page != 3:
        # add a return here for testing.
        new_page = int(curr_page) + 1
        return main_scrape(topic, url, new_page)
    else:
        return

def merge(csv1, csv2):
    master_df = pd.DataFrame()
    master_df.append(csv1)
    master_df.append(csv2)
    master_df.to_csv('/usr/src/app/quial.csv', index=False)

       
driver = main_driver()


### Production Script

field_names = ["basic-info", "meanings", "example-sentences"]

topics_list = []


filename = "../usr/src/app/topics.txt"
with open(filename) as file:
    topics_list = [line.rstrip() for line in file]

for i in range(0, len(topics_list)):
    file_name = topics_list[i]
    file_string2 = "../usr/src/app/" + file_name + "-quial2.csv"
    file_string = "../usr/src/app/" + file_name + "-quial.csv"
    
    with open(file_string2, mode="w") as csvfile:
        writer = csv.DictWriter(csvfile, fieldnames=field_names)
        writer.writeheader()

        
        main_scrape(topics_list[i], url, 1)
            
        for i in range(0, len(idiom_dict[file_name][0])):
                writer.writerow({
                    field_names[0]: clean(idiom_dict[file_name][0][i], no_emoji=True),
                    field_names[1]: clean(idiom_dict[file_name][1][i], no_emoji=True),
                    field_names[2]: clean(idiom_dict[file_name][2][i], no_emoji=True)
                })
                
    if os.path.exists(file_string):
        os.remove(file_string)
    os.rename(file_string2, file_string)



driver.close()

