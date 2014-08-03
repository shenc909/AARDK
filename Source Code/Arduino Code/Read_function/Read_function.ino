void setup() {
  Serial.begin(9600);Serial3.begin(9600);

}

/*
*1: Compass heading
*2: X-axis acceleration
*3: Y-axis acceleration
*4: Z-axis acceleration
*
*
*/

int add = 0;

void loop() {
  Serial.println(getFunction(5));
  //Serial3.write(1);
  //Serial.println(Serial3.read());
}




















int getCompass (int a){
  Serial3.write(a);
  add = 0;
  while(!Serial3.available());
  int test = Serial3.read();
  while(test==0){
    while(!Serial3.available());
    test = Serial3.read();
    add = 1;
  }
  if(add!=0){
    return(test+255);
  }
  else{
    return(test);
  }
}

int getFunction (int b){
  Serial3.write(b);
  if(b==1){
    int test = getCompass(1);
    return (test);
  }
  if(b==2||b==3||b==4||b==7||b==8||b==9){
    while(!Serial3.available());
    int power = Serial3.read();
  }
  while(!Serial3.available());
  int test = Serial3.read();
  return (test);
}

