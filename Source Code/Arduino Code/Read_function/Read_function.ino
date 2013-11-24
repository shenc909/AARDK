void setup() {
  Serial.begin(9600);Serial3.begin(9600);

}

int add = 0;

void loop() {
  Serial.println(getCompass(1));
}




















int getCompass (int a){
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

