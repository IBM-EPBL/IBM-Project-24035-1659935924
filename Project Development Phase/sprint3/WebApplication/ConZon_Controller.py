from flask import render_template, request, redirect, jsonify, Flask
from flask_mail import Mail

import ConZon_Verification

ConZon = Flask(__name__)
mails = Mail(ConZon)
ConZon.secret_key = '[%_CvOeNnZkOtNeCsOhN_%]'

ConZon.config['MAIL_SERVER'] = 'smtp.gmail.com'
ConZon.config['MAIL_PORT'] = 465
ConZon.config['MAIL_USERNAME'] = '20euit511@skcet.ac.in'
ConZon.config['MAIL_PASSWORD'] = 'venki2002'
ConZon.config['MAIL_USE_TLS'] = False
ConZon.config['MAIL_USE_SSL'] = True
mails = Mail(ConZon)


@ConZon.route('/')
def home_page():
    if request.cookies.get('ConZon_login') == 'True':
        print(request.cookies.get('userName'))
        return render_template('home_page.html', ConZon_user=request.cookies.get('userName'),
                               count_data=ConZon_Verification.containmentZone(),
                               count_data_without=ConZon_Verification.containmentZone_withoutCommon())
    else:
        return redirect('/admin/login')


@ConZon.route('/admin/login', methods=['POST', 'GET'])
def admin_login():
    if request.method == 'GET':
        return render_template("adminLogin.html")
    elif request.method == 'POST':
        username = request.form.get('mail')
        res = ConZon_Verification.admin_login_verification(username, request.form.get('password'))
        if res is True:
            res = redirect('/')
            print("ok")
            res.set_cookie('ConZon_login', 'True')
            res.set_cookie('userName', request.form.get('mail'))
            return res
        else:
            return render_template('adminLogin.html', data=res)


@ConZon.get('/logout')
def admin_logout():
    res = redirect('/')
    res.delete_cookie('ConZon_login')
    return res


@ConZon.route('/admin/registration', methods=['POST', 'GET'])
def admin_register():
    if request.method == 'GET':
        return render_template('adminRegistration.html')
    elif request.method == 'POST':
        res = ConZon_Verification.admin_register(request.form.get('mail'), request.form.get('password'),
                                                 request.form.get('reqid'))
        if res is True:
            return redirect('/')
        else:
            return render_template('adminRegistration.html', data=res)


@ConZon.route('/display_data_add', methods=['POST'])
def display_add():
    try:
        res = ConZon_Verification.dashboard_data_process(list(request.form.listvalues()))
        if res:
            return redirect('/')
        else:
            return 'Failed'
    except(type):
        return render_template('error.html', data=type)


@ConZon.route('/delete_data', methods=['POST'])
def display_delete():
    try:
        if ConZon_Verification.dashboard_data_delete(list(request.form.listvalues())):
            return redirect('/')
        else:
            return 'Failed'
    except(type):
        return render_template('error.html', data=type)


@ConZon.route('/display_data')
def display_datas():
    return jsonify({"data": ConZon_Verification.dashboard_data()})


if __name__ == '__main__':
    ConZon.run(debug=True)
