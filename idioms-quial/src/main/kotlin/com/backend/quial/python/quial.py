import undetected_chromedriver as uc
from selenium.webdriver.common.by import By
from selenium.common import exceptions
import pandas as pd

import string
import re
import csv
import os, os.path

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

    return uc.Chrome(options=options)


def get_additional_meaning(idiom, link):
    meanings_arr = []
    sentance_arr = []
    meaning_count = 0
    driver.get(link)
    list_elems = driver.find_elements(By.CSS_SELECTOR, 'li') # gets every list element on the page
    
    for i in range(0, len(list_elems)):
      if list_elems[i].text.lower() == idiom:
          for j in range(i+1, len(list_elems)): # loop to hone in the elements we want
              if idiom not in list_elems[j].text and meaning_count == 1: # loop exit
                  break
              elif idiom in list_elems[j].text: # example sentances
                  meaning_count = 1
                  sentance_arr.append(list_elems[j].text)
              elif idiom not in list_elems[j].text and meaning_count == 0 and j < i+5: # meanings
                  meanings_arr.append(list_elems[j].text)
                   
    return [meanings_arr, sentance_arr]


def get_meaning(idiom):
    arr = []
    idiom_arr = idiom.split('\n')
    idiom_arr[1] = re.sub(r"\(.*?\)","()" ,idiom_arr[1]) # removes everything inside ()
    idiom_arr[1] = idiom_arr[1].replace("()", "").strip() # removes () themselves
    idiom_arr[2] = idiom_arr[2].replace(" Read more ➺", "")
    arr.append(idiom_arr)
    return arr
    

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

    idioms_list = [text.text for text in idioms]
    links_list = [link.get_attribute('href') for link in idioms_links]
    
    for i in range(0, len(idioms_list)):
        idiom_dict[letter][0] += get_meaning(idioms_list[i])
        meanings_list = get_additional_meaning(idioms_list[i].split('\n')[0], links_list[i])
        idiom_dict[letter][1] += [meanings_list[0]]
        idiom_dict[letter][2] += [meanings_list[1]]
    
    if curr_page != max_page:
        new_page = int(curr_page) + 1
        return scrape(letter, new_page)
    else:
        return

def merge(csv1, csv2):
    master_df = pd.DataFrame()
    master_df.append(csv1)
    master_df.append(csv2)
    master_df.to_csv('quial.csv', index=False)
    

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


field_names = ["basic-info", "meanings", "example-sentences"]


with open("/usr/src/app/quial2.csv", mode="w") as csvfile:
    writer = csv.DictWriter(csvfile, fieldnames=field_names)
    writer.writeheader()
    
    for letter in alphabet:
        print(letter)
        scrape(letter, 1)
        for i in range(0, len(idiom_dict[letter][0])):
            writer.writerow({
            field_names[0]: idiom_dict[letter][0][i],
            field_names[1]: idiom_dict[letter][1][i],
            field_names[2]: idiom_dict[letter][2][i]
            })


if os.path.exists("/usr/src/app/quial.csv"):
    os.remove("/usr/src/app/quial.csv")

'''
if os.path.exists("/usr/src/app/user-quial.csv"):
    merge("/usr/src/app/quial2.csv", "/usr/src/app/user-quial.csv")
    os.remove("/usr/src/app/quial2.csv")
else:
    os.rename("/usr/src/app/quial2.csv", "/usr/src/app/quial.csv")
'''


os.rename("/usr/src/app/quial2.csv", "/usr/src/app/quial.csv")

'''
count = 0

for letter in alphabet:
    print(letter)
    scrape(letter, 1)
    for item in idiom_dict[letter][0]:
        count = count + len(item)

print("idiom count is: ", str(count))
'''

driver.close()
