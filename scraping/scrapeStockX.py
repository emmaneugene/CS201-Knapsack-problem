import selenium
from selenium import webdriver
import time
import json

chrome_options = webdriver.ChromeOptions()
# chrome_options.add_argument("--headless")
chrome_options.add_argument("--no-sandbox")
chrome_options.add_argument("window-size=1280,720")
chrome_options.add_argument(f'user-agent=Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2224.3 Safari/537.36')
chrome_options.add_argument("--disable-gpu")
chrome_options.add_argument("--disable-dev-shm-usage")

driver = webdriver.Chrome(executable_path="./chromedriver", options=chrome_options)

data = json.load(open("cleaned_goat.json"))



for shoe_name in sorted(list(data.keys()))[398:]:
    url = "https://stockx.com/"
    driver.get(url)
    time.sleep(5)
    try:
        driver.find_element_by_css_selector("div#px-captcha")
        captcha = input("Press enter when done with captcha")
    except:
        pass

    try:
        driver.find_element_by_css_selector("img.css-10pt21e.et9rxoe7").click()
    except:
        pass

    driver.find_element_by_css_selector("input#home-search").send_keys(shoe_name)
    time.sleep(5)

    try:
        driver.find_element_by_css_selector("#list-footer")
        continue
    except:
        pass

    try:
        driver.find_element_by_css_selector("div.list-item-content").click()
        time.sleep(5)
        if len(driver.find_elements_by_css_selector("div.stat-small")) > 0:
            with open("stockx.txt", "a+") as fp:
                fp.write(shoe_name + "\n")
                print (shoe_name)
                for d in driver.find_elements_by_css_selector("div.stat-small"):
                    if d.text != "":
                        print (d.text)
                        fp.write(d.text + "\n")
    except:
        pass

driver.quit()