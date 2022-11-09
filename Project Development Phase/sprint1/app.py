import adminverification
from flask import Flask, render_template, request, redirect, session

app = Flask(__name__)

app.secret_key = 'venki2002'


@app.route('/')
def home_page():
    if session.get('conzo_login'):
        return render_template('home_page.html', conzo_Mail=session.get('conzo_Mail'))
    else:
        return redirect('/admin')


@app.route('/admin', methods=['POST', 'GET'])
def admin_login():
    if request.method == 'GET':
        return render_template("adminlogin.html")
    elif request.method == 'POST':
        username = request.form.get('mail')
        res = adminverification.adminloginverfication(username, request.form.get('password'))
        if res:
            session['conzo_login'] = True
            session['conzo_Mail'] = username
            return redirect('/')
        else:
            return render_template('adminlogin.html', data=res)


@app.get('/logout')
def admin_logout():
    session.pop('conzo_login', None)
    session.pop('conzo_Mail', None)
    return redirect('/')


@app.route('/admin/registration', methods=['POST', 'GET'])
def admin_register():
    if request.method == 'GET':
        return render_template('adminRegistration.html')
    elif request.method == 'POST':
        res = adminverification.adminRegister(request.form.get('mail'), request.form.get('password'), request.form.get('reqid'))
        if res:
            return redirect('/')
        else:
            return render_template('adminRegistration.html', data=res)


if __name__ == '__main__':
    app.run(debug=True)
