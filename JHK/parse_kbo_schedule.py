import os
from bs4 import BeautifulSoup
import pandas as pd

# 개별 HTML 파일에서 경기 정보를 파싱하는 함수
def parse_schedule_from_html(file_path):
    with open(file_path, 'r', encoding='utf-8') as f:
        soup = BeautifulSoup(f, 'html.parser')

    games = []
    # 각 날짜 칸을 선택 (달력 형태의 table 구조)
    days = soup.select('.calendar_area tbody td')

    for day in days:
        # 날짜 정보 가져오기 (예: 1, 2, 3...)
        date_tag = day.find('span', class_='day')
        if not date_tag:
            continue
        day_num = date_tag.text.strip()

        # 경기 정보가 있는 div 찾기
        inner = day.find('div', class_='inner')
        if not inner:
            continue

        # 각 경기 정보를 담고 있는 <li> 태그들
        game_items = inner.select('div.games > ul > li')
        for game in game_items:
            anchors = game.find_all('a')
            if not anchors:
                continue

            a = anchors[0]

            # 팀 이름 정보 가져오기
            teams = a.find_all('span', class_='team')
            if len(teams) != 2:
                continue
            team1 = teams[0].text.strip()
            team2 = teams[1].text.strip()

            # 점수 정보 가져오기
            score_tags = a.find_all('span', class_='score')
            weather = a.find('span', class_='weather')

            # 우천취소 여부 판단
            rainout = weather and '우천취소' in weather.text

            score1 = score_tags[0].text.strip() if score_tags else ''
            score2 = score_tags[1].text.strip() if len(score_tags) > 1 else ''

            # 승자 판별 (score lead 클래스로 구분)
            lead_scores = a.find_all('span', class_='score lead')
            winner = ''
            if lead_scores:
                if lead_scores[0] == score_tags[0]:
                    winner = team1
                else:
                    winner = team2

            # 결과를 하나의 딕셔너리로 저장
            games.append({
                'day': day_num,
                'team1': team1,
                'score1': score1,
                'team2': team2,
                'score2': score2,
                'rainout': rainout,
                'winner': winner
            })

    return games

# 폴더 내 모든 HTML 파일을 파싱해서 하나의 CSV로 저장하는 함수
def parse_all_html_in_folder(folder_path, output_csv='schedule_all.csv'):
    all_games = []
    for filename in sorted(os.listdir(folder_path)):
        if filename.endswith('.html'):
            full_path = os.path.join(folder_path, filename)

            # 개별 HTML 파일에서 경기 데이터 추출
            games = parse_schedule_from_html(full_path)

            # 파일명에서 월(month) 정보 추출
            month = filename.replace('schedule_2025_', '').replace('.html', '')
            for game in games:
                game['month'] = month

            all_games.extend(games)

    # 데이터프레임으로 변환 후 날짜 칼럼 구성
    df = pd.DataFrame(all_games)
    df['date'] = '2025-' + df['month'].str.zfill(2) + '-' + df['day'].str.zfill(2)

    # 최종 컬럼 순서 설정
    df = df[['date', 'team1', 'score1', 'team2', 'score2', 'winner', 'rainout']]

    # CSV로 저장
    df.to_csv(output_csv, index=False, encoding='utf-8-sig')
    print(f'Saved {len(df)} games to {output_csv}')

# CLI로 실행할 수 있도록 설정
if __name__ == '__main__':
    import argparse

    parser = argparse.ArgumentParser()
    parser.add_argument('--folder', type=str, required=True, help='HTML files folder path')  # 필수 인자
    parser.add_argument('--out', type=str, default='schedule_all.csv', help='Output CSV file')  # 선택 인자

    args = parser.parse_args()
    parse_all_html_in_folder(args.folder, args.out)
