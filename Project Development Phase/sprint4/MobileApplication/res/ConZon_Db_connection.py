import ibm_db

conn = ibm_db.connect(
    "{IBM DB2 ODBC DRIVER};DATABASE=bludb;HOSTNAME=0c77d6f2-5da9-48a9-81f8-86b520b87518.bs2io90l08kqb1od8lcg.databases.appdomain.cloud;PORT=31198;SECURITY=SSL;SSLServerCertificate=DigiCertGlobalRootCA.crt;PROTOCOL=TCPIP;UID=hvs11698;PWD=xHqh4sBBGY10Ci3V",
    '', '')


def execution(cmd):
    return ibm_db.execute(cmd)


def execution_immediate(cmd):
    return ibm_db.exec_immediate(conn, cmd)


def Prepare_db(cmd):
    return ibm_db.prepare(conn, cmd)
