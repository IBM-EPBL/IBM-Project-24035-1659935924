from flask import Flask, render_template

Appli = Flask(__name__)


@Appli.route('/login')
def login():
    return render_template('html/login.html')


@Appli.route('/Reg')
def Registration():
    return render_template('html/Registration.html')


@Appli.route('/About')
def about():
    return render_template('html/About.html')


if __name__ == '__main__':
    Appli.run()
