import asyncio
from playwright.async_api import async_playwright
import os

async def fetch_monthly_schedule(year: int, month: int):
    url = f"https://statiz.sporki.com/schedule/?year={year}&month={month}"
    filename = f"schedule_{year}_{month:02}.html"
    print(f"📆 Fetching {url}")

    async with async_playwright() as p:
        browser = await p.chromium.launch(headless=True)
        page = await browser.new_page()
        await page.goto(url)
        await page.wait_for_timeout(3000)

        html = await page.content()

        with open(filename, "w", encoding="utf-8") as f:
            f.write(html)
            print(f"✅ Saved to {filename}")

        await browser.close()

async def run_all():
    year = 2025
    for month in range(3, 11):  # 3~10월
        await fetch_monthly_schedule(year, month)

if __name__ == "__main__":
    asyncio.run(run_all())
