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

alphabet = list(string.ascii_lowercase)
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


def get_additional_meaning(idiom, link):
    driver.get(link)

    ul_elems = driver.find_elements(By.CSS_SELECTOR, 'ul')
    ol_elems = driver.find_elements(By.CSS_SELECTOR, 'ol')

    
    ul_elems_comp = [elem.text for elem in ul_elems]
    ol_elems_comp = [elem.text for elem in ol_elems]
    
              
    return [ul_elems_comp[1].split('\n'), ol_elems_comp[1].split('\n')]


def get_meaning(idiom):
    idiom_arr = idiom.split('\n')
    idiom_arr[1] = re.sub(r"\(.*?\)","()" ,idiom_arr[1]) # removes everything inside ()
    idiom_arr[1] = idiom_arr[1].replace("()", "").strip().replace(" Read more ➺", "") # removes () themselves
    if (len(idiom_arr) == 3):
        idiom_arr[2] = idiom_arr[2].replace(" Read more ➺", "")
        
    return idiom_arr
    

def scrape(letter, page):
    if page == 1:
        idiom_dict[letter] = [[], [], []]
        
    idiom_for_letter = []
    driver.get(url + letter + "/page/" + str(page))

    page_info = driver.find_elements(By.CSS_SELECTOR, 'p.pno')

    curr_page = page_info[0].text.split()[1]
    max_page = page_info[0].text.split()[-1]

    idioms = driver.find_elements(By.CSS_SELECTOR, "div.idiom")
    idioms_links = driver.find_elements(By.CSS_SELECTOR, "a.rm")
    alt_links = driver.find_elements(By.CSS_SELECTOR, "p.idt")
    
    alt_links_list = [link.find_elements(By.CSS_SELECTOR, "a") for link in alt_links]
    alt_links_list2 = []
    
    
    for i in range(0, len(alt_links_list)):
        alt_links_list2.append(alt_links_list[i][0].get_attribute('href'))
    

    idioms_list = [text.text for text in idioms]
    links_list = [link.get_attribute('href') for link in idioms_links]
    
    for i in range(0, len(idioms_list)):
        if (i < len(links_list) - 1):
            basic_info = get_meaning(idioms_list[i])
            meanings_list = get_additional_meaning(idioms_list[i].split('\n')[0], links_list[i])
            
            
            if (len(basic_info) == 2):
                meanings_list = get_additional_meaning(idioms_list[i].split('\n')[0], alt_links_list2[i])
                
                if "Meaning: " not in basic_info[1]:
                     replacement_meaning = "Meaning: " + meanings_list[0][0]
                     basic_info.insert(1, replacement_meaning)
                elif "Example: " not in basic_info[1]:
                    replacement_example = "Example: " + meanings_list[1][0]
                    basic_info.insert(2, replacement_example)

           
            idiom_dict[letter][0] += [basic_info]
            idiom_dict[letter][1] += [meanings_list[0]]
            idiom_dict[letter][2] += [meanings_list[1]]
        else:
            break
    
    if curr_page != max_page:
        # add a return here for testing.
        new_page = int(curr_page) + 1
        return scrape(letter, new_page)
    else:
        return

def merge(csv1, csv2):
    master_df = pd.DataFrame()
    master_df.append(csv1)
    master_df.append(csv2)
    master_df.to_csv('/usr/src/app/quial.csv', index=False)

       
driver = main_driver()


'''      

### Testing Script

scrape('a', 1)

print(len(idiom_dict['a'][0]))

for i in range(0, len(idiom_dict['a'][0])):
    print(idiom_dict['a'][0][i])
    print(idiom_dict['a'][1][i])
    print(idiom_dict['a'][2][i], "\n")

'''


'''

### Testing Script 2

field_names = ["basic-info", "meanings", "example-sentences"]

with open("/usr/src/app/quial.csv", mode="w") as csvfile:
    writer = csv.DictWriter(csvfile, fieldnames=field_names)
    writer.writeheader()   
    
    scrape("a", 1)
    for i in range(0, len(idiom_dict["a"][0])):
        writer.writerow({
        field_names[0]: idiom_dict["a"][0][i],
        field_names[1]: idiom_dict["a"][1][i],
        field_names[2]: idiom_dict["a"][2][i]
        })
        break
        

'''


### Production Script

field_names = ["basic-info", "meanings", "example-sentences"]
try:
    with open("/usr/src/app/quial2.csv", mode="w") as csvfile:
        writer = csv.DictWriter(csvfile, fieldnames=field_names)
        writer.writeheader()

      
        for letter in alphabet:
            print(letter)
            scrape(letter, 1)
            for i in range(0, len(idiom_dict[letter][0])):
                writer.writerow({
                field_names[0]: clean(idiom_dict[letter][0][i], no_emoji=True),
                field_names[1]: clean(idiom_dict[letter][1][i], no_emoji=True),
                field_names[2]: clean(idiom_dict[letter][2][i], no_emoji=True)
                })
            

    if os.path.exists("/usr/src/app/quial.csv"):
        os.remove("/usr/src/app/quial.csv")
        
    if os.path.exists("/usr/src/app/user-quial.csv"):
        merge("/usr/src/app/quial2.csv", "/usr/src/app/user-quial.csv")
        os.remove("/usr/src/app/quial2.csv")
    else:
        os.rename("/usr/src/app/quial2.csv", "/usr/src/app/quial.csv")
        os.remove("/usr/src/app/quial2.csv")
except Exception as e:
    print(e)
    

'''
### Idiom Count
count = 0

for letter in alphabet:
    print(letter)
    scrape(letter, 1)
    for item in idiom_dict[letter][0]:
        count = count + len(item)

print("idiom count is: ", str(count))

'''

driver.close()
