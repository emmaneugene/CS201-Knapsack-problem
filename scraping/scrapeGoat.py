import selenium
from selenium import webdriver
import time
# from fake_useragent import UserAgent

# ua = UserAgent(verify_ssl=False)
# ua.update()
# userAgent = ua.random
# print (userAgent)
chrome_options = webdriver.ChromeOptions()
# chrome_options.add_argument("--headless")
chrome_options.add_argument("--no-sandbox")
chrome_options.add_argument("window-size=1280,720")
chrome_options.add_argument(f'user-agent=Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2224.3 Safari/537.36')
chrome_options.add_argument("--disable-gpu")
chrome_options.add_argument("--disable-dev-shm-usage")

driver = webdriver.Chrome(executable_path="./chromedriver", options=chrome_options)

driver.get("https://www.goat.com/sneakers")

try:
    driver.find_element_by_css_selector("button.fpAmCQ").click()
except:
    pass

fp = open("output.txt", "a")

count = 0

while count < 100:
    time.sleep(2)

    try:
        if driver.find_element_by_css_selector("div.g-recaptcha") != False:
            input("Please press enter when done: ")
    except:
        pass

    try:
        for div in driver.find_elements_by_css_selector("div.grid-cell"):
            fp.write(div.text)
            fp.write("\n")
    except:
        pass

    driver.execute_script("window.scrollTo(0, 500);")

    time.sleep(5)

    driver.find_element_by_css_selector("#root > div > div.PageLayout__PageAlignment-sc-1s1ahmo-1.gOnmNv > div.flex.full-width.border-box.relative.column.align-center.justify-start.hide-scroll.goat-width > div.filter-wrapper.desktop.AlgoliaProductGrid__SearchFiltersPositioned-gu3y8q-0.gdylGG > div.filter-content-area > div > button").click()

    time.sleep(5)

    count += 1

fp.close()


