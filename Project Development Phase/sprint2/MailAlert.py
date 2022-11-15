from flask_mail import Mail, Message

from app import ConZon

app = ConZon
mails = Mail(app)

app.config['MAIL_SERVER'] = 'smtp.gmail.com'
app.config['MAIL_PORT'] = 465
app.config['MAIL_USERNAME'] = '20euit511@skcet.ac.in'
app.config['MAIL_PASSWORD'] = 'venki2002'
app.config['MAIL_USE_TLS'] = False
app.config['MAIL_USE_SSL'] = True
mails = Mail(app)


def index(message, bmessage, remail):
    msg = Message(message,
                  sender='20euit511@skcet.ac.in',
                  recipients=[remail]
                  )
    msg.body = bmessage
    mails.send(msg)
    return 'sent'
