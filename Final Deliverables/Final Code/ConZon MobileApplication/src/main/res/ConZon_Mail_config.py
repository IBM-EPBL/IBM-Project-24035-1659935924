from flask_mail import Message

from ConZon_Controller import mails

message = 'sample'
bmessage = 'sample'
remail = 'sample'


def assing_mail(messages, bmessages, remails):
    global message, bmessage, remail
    message = messages
    bmessage = bmessages
    remail = remails
    index()


def index():
    msg = Message(message, sender='20euit511@skcet.ac.in', recipients=[remail])
    msg.body = bmessage
    mails.send(msg)
    return 'sent'
