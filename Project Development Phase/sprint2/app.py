from flask import Flask, render_template, request, redirect, session, jsonify

import adminverification

ConZon = Flask(__name__)
ConZon.secret_key = 'Venki@2002'


@ConZon.route('/')
def home_page():
    if request.cookies.get('ConZon_login') == 'True':
        print(request.cookies.get('userName'))
        return render_template('home_page.html', ConZon_user=request.cookies.get('userName'),
                               count_data=adminverification.containmentZone())
    else:
        return redirect('/admin/login')


@ConZon.route('/admin/login', methods=['POST', 'GET'])
def admin_login():
    if request.method == 'GET':
        return render_template("adminLogin.html")
    elif request.method == 'POST':
        username = request.form.get('mails')
        res = adminverification.admin_login_verification(username, request.form.get('password'))
        if res is True:
            res = redirect('/')
            session['ConZon_login'] = True
            res.set_cookie('ConZon_login', 'True')
            res.set_cookie('userName', request.form.get('mails'))
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
        res = adminverification.admin_register(request.form.get('mail'), request.form.get('password'),
                                               request.form.get('reqid'))
        if res is True:
            return redirect('/')
        else:
            return render_template('adminRegistration.html', data=res)


@ConZon.route('/display_data_add', methods=['POST'])
def display_add():
    if adminverification.dashboard_data_process(list(request.form.listvalues())):
        return redirect('/')
    else:
        return 'Failed'


@ConZon.route('/delete_data', methods=['POST'])
def display_delete():
    if adminverification.dashboard_data_delete(list(request.form.listvalues())):
        return redirect('/')
    else:
        return 'Failed'


@ConZon.route('/display_data')
def display_datas():
    return jsonify({"data": adminverification.dashboard_data()})


if __name__ == '__main__':
    ConZon.run(debug=True)
