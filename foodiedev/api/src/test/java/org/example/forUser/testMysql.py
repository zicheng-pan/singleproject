import pymysql

con = pymysql.connect(
	host='zz.pan',
	user='root',
	passwd='mysecret-pw',
	port=7066,
	db='foodie-shop-dev',
	charset='utf8'
	)

cursor = con.cursor()
cursor.execute('SELECT * FROM `users`')
rest = cursor.fetchone()
print(rest)