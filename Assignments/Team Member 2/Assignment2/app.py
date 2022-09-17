from  flask import Flask,render_template
wep = Flask(__name__)

@wep.route('/')
def a():  # put application's code here
    return render_template('html/homePage.html')


@wep.route('/about')
def b():  # put application's code here
    return render_template('html/aboutPage.html')

@wep.route('/sign')
def c():  # put application's code here
    return render_template('html/signPage.html')

@wep.route('/sigup')
def d():  # put application's code here
    return render_template('html/sigupPage.html')


if __name__ == '__main__':
    wep.run()