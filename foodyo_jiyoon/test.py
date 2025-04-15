from selenium.webdriver.common.by import By
from bs4 import BeautifulSoup
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
import time
import csv
import json  # JSON 출력 위해 추가
import sys

url = 'https://map.kakao.com/'
driver = webdriver.Chrome()
driver.get(url)

if len(sys.argv) > 1:
    searchloc = sys.argv[1]
else:
    searchloc = "맛집"

# 음식점 입력 후 찾기 버튼 클릭 
search_area = driver.find_element(By.XPATH, '//*[@id="search.keyword.query"]')
search_area.send_keys(searchloc)
driver.find_element(By.XPATH, '//*[@id="search.keyword.submit"]').send_keys(Keys.ENTER)
time.sleep(2)

# 장소 버튼 클릭 
driver.find_element(By.XPATH, '//*[@id="info.main.options"]/li[2]/a').send_keys(Keys.ENTER)

def storeNamePrint(page):
    time.sleep(0.2)

    html = driver.page_source
    soup = BeautifulSoup(html, 'html.parser')

    store_lists = soup.select('.placelist > .PlaceItem')
    list = []

    for store in store_lists:
        temp = []
        name = store.select('.head_item > .tit_name > .link_name')[0].text
        degree = store.select('.rating > .score > .num')[0].text
        reviewCount = store.select('.rating > .score > .numberofscore')[0].text
        addr = store.select('.info_item > .addr')[0].text.splitlines()[1]
        tel = store.select('.info_item > .contact > .phone')[0].text

        temp.append(name)
        temp.append(degree)
        temp.append(reviewCount)
        temp.append(addr)
        temp.append(tel)

        list.append(temp)

    if page == 1:
        f = open('store_list_1.csv', 'w', encoding='utf-8-sig', newline='')
        writercsv = csv.writer(f)
        header = ['name', 'degree', 'reviewCount', 'address', 'tel']
        writercsv.writerow(header)

        for i in list:
            writercsv.writerow(i)
    else:
        f = open('store_list_1.csv', 'a', encoding='utf-8-sig', newline='')
        writercsv = csv.writer(f)

        for i in list:
            writercsv.writerow(i)

    return list


result = []

result += storeNamePrint(1)

# 다음 페이지 반복
try:
    btn = driver.find_element(By.CSS_SELECTOR, '.more')
    driver.execute_script("arguments[0].click();", btn)

    for i in range(2, 6):
        xPath = '//*[@id="info.search.page.no' + str(i) + '"]'
        driver.find_element(By.XPATH, xPath).send_keys(Keys.ENTER)
        time.sleep(1)

        result += storeNamePrint(i)
except:
    print('ERROR!')

json_output = []
for i in result:
    json_output.append({
        "name": i[0],
        "degree": i[1],
        "reviewCount": i[2],
        "address": i[3],
        "tel": i[4]
    })

print(json.dumps(json_output, ensure_ascii=False))  