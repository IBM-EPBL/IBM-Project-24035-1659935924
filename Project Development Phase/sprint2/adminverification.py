import mariadb

import MailAlert

# Db connection
config = {
    'host': '127.0.0.1',
    'port': 3306,
    'user': 'root',
    'password': '',
    'database': 'conzon'
}
conn = mariadb.connect(**config)
cur = conn.cursor()


def db_con(command):
    cur.execute(command)
    conn.commit()


def admin_login_verification(name, password):
    db_con('Select * from admin_profile where mail_id ="' + name + '"')
    if cur.fetchone() is not None:
        db_con('Select password from admin_profile where mail_id ="' + name + '"')
        if password == cur.fetchone()[0]:
            return True
        else:
            return 'Password is Incorrect 🙁'

    return 'Account Not Found 😞'


def admin_register(name, passwords, req_id):
    if req_id == '2002':
        db_con('Select mail_id from admin_profile where mail_id ="' + name + '"')
        if cur.fetchone() is not None:
            return "⚠️Account is already available"
        else:
            execute = "INSERT INTO admin_profile (mail_id,password,Hospital_ID) VALUES ('" + name + "','" + passwords + "','" + req_id + "')"
            db_con(execute)
            MailAlert.index('Account was successfully Created', 'ConZo Account Creation', name)
            return True
    else:
        return "Hospital ID is Incorrect 😟"


def dashboard_data():
    db_con('Select * from containment_details LIMIT 5')
    rs = cur.fetchall()
    employee = []
    content = {}
    for result in rs:
        content = {'ID': result[0], 'Name': result[1], 'City': result[2], 'Latitude': result[3],
                   'Longitude': result[4], 'Address': result[5]}
        employee.append(content)
        content = {}
    return employee


def dashboard_data_add(name, city, latitude, longitude, address):
    execute = "INSERT INTO containment_details (name, city, latitude, longitude, address) VALUES ('" + name + "','" + city + "','" + latitude + "','" + longitude + "','" + address + "')"
    db_con(execute)


def dashboard_data_process(data):
    try:
        res_data = []
        row_len = len(data)
        col_len = len(data[0])
        for colCnt in range(col_len):
            for rowCnt in range(row_len):
                res_data.insert(rowCnt, data[rowCnt][colCnt])
            dashboard_data_add(res_data[0], res_data[1], res_data[2], res_data[3], res_data[4])
            res_data.clear()
            print(dashboard_data())
        return True
    except():
        return False


def dashboard_data_delete(data):
    print(data)
    try:
        col_len = len(data[0])
        for colCnt in range(col_len):
            db_con('DELETE FROM containment_details WHERE Address ="' + data[4][colCnt] + '"')
        return True
    except():
        return False


def containmentZone():
    db_con('select COUNT(*) from containment_details')
    return cur.fetchone()[0]
