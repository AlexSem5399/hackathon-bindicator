import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import lejos.hardware.device.UART;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

public class BinDay {
	private static BaseRegulatedMotor mL;
	private static BaseRegulatedMotor mR;
	
	private static Random random = new Random();

	private static MovePilot getPilot(Port left, Port right, int diam, int offset) {
        mL = new EV3LargeRegulatedMotor(left);
        Wheel wL = WheeledChassis.modelWheel(mL, diam).offset(-1 * offset);
        mR = new EV3LargeRegulatedMotor(right);
        Wheel wR = WheeledChassis.modelWheel(mR, diam).offset(offset);
        Wheel[] wheels = new Wheel[] {wR, wL};
        Chassis chassis = new WheeledChassis(wheels, WheeledChassis.TYPE_DIFFERENTIAL);
        return new MovePilot(chassis);
    }
	
	private static void move() {
		MovePilot pilot = getPilot(MotorPort.A, MotorPort.B, 56, 54);
		pilot.setLinearSpeed(9999999);
		pilot.setAngularSpeed(9999999);
		pilot.setLinearAcceleration(9999999);
		pilot.setAngularAcceleration(9999999);
	  
		double randomTravel;
		double randomRotate;
	  
		for (int i = 0; i < 10; i++) {
			// Sound.playSample(new File("scream4.wav"), Sound.VOL_MAX);
			randomTravel = random.nextInt(125) + 75; // 75 - 175
			randomRotate = random.nextInt(720) - 360;// -360 - 360
			pilot.travel(randomTravel);
			pilot.rotate(randomRotate);
		}
	}
	
	private static void lights(byte[] signal) {
		try {
			Port port = SensorPort.S1;
			UART uart = new UART(port);
			
			uart.setBitRate(115200);
			
			try {
				uart.write(signal, 0, 1);
			} finally {
				uart.close();
			}
			
		}
		catch (Exception e){
			// dw our code always works
		}
	}
	
	private static void waitForBinDay() {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            LCD.drawString("Waiting for", 0, 2);
            LCD.drawString("bin day...", 0, 3);

            Socket clientSocket = serverSocket.accept();
            
            LCD.drawString("Hang on a minute...", 0, 3);
            Delay.msDelay(2000);
            
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String message = in.readLine();
            
            LCD.clear();
        	LCD.drawString("ITS BIN DAY!!!", 0, 2);
        	
        	byte[] signal = null;
        	if (message.equals("R")) {
        		signal = new byte[] { 1 };
        	} else if (message.equals("T")) {
        		signal = new byte[] { 0 };
        	}
        	
            lights(signal);
            move();


            in.close();
            
            clientSocket.close();
            serverSocket.close();
        } catch (Exception e) {
            // dw we know what we're doing
        }
	}
	
	public static void main(String[] args) {
		waitForBinDay();
	}

}
