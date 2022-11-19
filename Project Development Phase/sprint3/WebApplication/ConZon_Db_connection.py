import ibm_db

conn = ibm_db.connect(
    "DRIVER={IBM DB2 ODBC DRIVER};DATABASE=bludb;HOSTNAME=815fa4db-dc03-4c70-869a-a9cc13f33084.bs2io90l08kqb1od8lcg.databases.appdomain.cloud;PORT=30367;SECURITY=SSL;SSLServerCertificate=DigiCertGlobalRootCA.crt;PROTOCOL=TCPIP;UID=kwv60428;PWD=UYXJsMmfbio7KCY4",
    '', '')


def execution(cmd):
    return ibm_db.execute(cmd)


def execution_immediate(cmd):
    return ibm_db.exec_immediate(conn, cmd)


def Prepare_db(cmd):
    return ibm_db.prepare(conn, cmd)

