import sys
import telnetlib
import time

step = 5;
HOST = "localhost"
port = "5554"
password = "auth activity\r\n".encode('ascii')
trasax=['16.9507','16.9512','16.9520']
trasay=['52.403309','52.40379','52.40347']

#user = raw_input("Enter your remote account: ")

#password = getpass.getpass()

tn = telnetlib.Telnet(HOST, port)
print("polaczono z "+HOST+"!")
tn.write(password)

print("symulacja GPS")
for i in range(len(trasax)):
	tn.write(("geo fix "+trasax[i]+" "+trasay[i]+"\r\n").encode('ascii')) 
	print("wpisano punkt "+str(i))
	time.sleep(step)
#tn.write("geo fix 16.9507 52.403309\r\n".encode('ascii'))
#time.sleep(step)
#tn.write("geo fix 16.9512 52.40379\r\n".encode('ascii'))
#time.sleep(step)
#tn.write("geo fix 16.95230 52.40305\r\n".encode('ascii'))


input("exit")
