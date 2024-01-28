int voltage;
int leds;

void setup() {
  // initialize serial communication:
  Serial.begin(115200);
  pinMode(6, OUTPUT);
  pinMode(7, OUTPUT);
}

void loop() {
  if (Serial.available() > 0) {
    int binByte = Serial.read();
    digitalWrite(6, LOW);
    digitalWrite(7, LOW);
    if (binByte == 0) {
      for (int i = 0; i < 20; i++) {
        digitalWrite(6, HIGH);
        delay(100);
        digitalWrite(6, LOW);
        delay(100);
      }
    } else {
      for (int i = 0; i < 20; i++) {
        digitalWrite(7, HIGH);
        delay(100);
        digitalWrite(7, LOW);
        delay(100);
      }
    }

  }

}