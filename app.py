import datetime as dt
from datetime import date
import datetime
from flask import *
import pandas as pd
from pandas_datareader import data, wb
# from polygon import WebSocketClient, STOCKS_CLUSTER
import psycopg2

app = Flask(__name__)

poly_key = "p3FIfR0704Chcp7W2vHwluUOIhVN4EhD"

@app.route('/')
def hello_world():
    return "Hello, world"

@app.route('/stock/<stock>')
def get_stock(stock):
    start = datetime.date(year=2022, month=4, day=1)
    today = date.today()
    try:
        stockInfo = data.DataReader(stock, 'yahoo', start, today)
    except Exception as e:
        return "Invalid Ticker"
    stockJSON = stockInfo.iloc[-1].to_json()
    return stockJSON


if __name__ == '__main__':
    app.run()
