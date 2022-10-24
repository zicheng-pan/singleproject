import pymysql

con = pymysql.connect(
	host='1.116.154.207',
	user='root',
	passwd='my-secret-pw',
	port=3306,
	db='foodie-shop-dev',
	charset='utf8'
	)

cursor = con.cursor()
cursor.execute('SELECT * FROM `users`')
rest = cursor.fetchone()
print(rest)