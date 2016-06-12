import sys
import telnetlib
import time

#zwraca punkt pomiedzy punktami (x0,y0), (x1,y1)
def interpolate(x0,y0,x1,y1):
    if x0>= x1:
        tmp=x0
        x0=x1
        x1=tmp
    if y0>= y1:
        tmp=y0
        y0=y1
        y1=tmp
    x=(x0+x1)/2
    y=y0+((y1-y0)/(x1-x0))*(x-x0)
    return (x,y)

#przyjmuje string, zwraca string
#wywołuje interpolateList
def interpolateListSTR(x0,y0,x1,y1):
        return [['%0.5f' % res for res in results] for results in interpolateList(float(x0),float(y0),float(x1),float(y1))]

# zwraca liste 3 punktów pomiedzy oraz 2 granicznych
def interpolateList(x0,y0,x1,y1):
        x,y=interpolate(x0,y0,x1,y1)
        return((x0,y0),interpolate(x0,y0,x,y),(x,y),interpolate(x,y,x1,y1),(x1,y1))
    
if __name__ == "__main__":
        SIMULATE=False;
        step = 5;
        HOST = "localhost"
        PORT = "5554"
        password = "auth activity\r\n".encode('ascii')
        trasax=['16.9507'  ,'16.9512' ,'16.9520' ,'16.952764',' 16.95387 ',' 16.954652 ','16.954872 ','16.955247 ','16.95588  ','16.956867 ','16.958096  ','16.959566 ','16.960618 ','16.961508',' 16.962639 ',' 16.963245','  16.96402  ',' 16.964755  ',' 16.965506  ',' 16.966433   ',' 16.967892 ',' 16.969512 ',' 16.971036    ',' 16.971787 ',' 16.972409 ',' 16.973234    '   ] 
        trasay=['52.403309','52.40379','52.40347','52.403479',' 52.403433 ','52.403414 ','52.403738 ','52.403829 ','52.403764 ','52.403627  ','52.403466 ','52.40328 ','52.403142  ','52.402916  ','52.402612','  52.40247','  52.402288  ',' 52.4021    ',' 52.401902   ',' 52.401653 ',' 52.401313 ',' 52.400835 ',' 52.400475    ',' 52.400331  ',' 52.400187  ',' 52.400056 '            ]


        if not SIMULATE:
                tn = telnetlib.Telnet(HOST, PORT)
        print("polaczono z "+HOST+"!")
        if not SIMULATE:
                tn.write(password)

        print("symulacja GPS")
        print("wpisano punkt startowy")
        tn.write(("geo fix "+trasax[0]+" "+trasay[0]+"\r\n").encode('ascii'))
           
                

