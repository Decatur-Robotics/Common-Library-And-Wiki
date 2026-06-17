public class Train {
	public class train extends car {
		public String voltage;
		private double rotation;
		private double current;

		public shooter(){
            this.voltage = "12V";
            this.rotation = 0.0;
            this.current = 0.0;

        }

		public void periodic() {
			io.updateInputs(null);
			Logger.processInputs(null, null);

			if (isEStopped) {
				io.stop();
			}

		}

		setVel(DriveTrainTalonFX logan){
			this.rotation = rotation;
			logan.setVoltage(rotation);
		}

		public double getRotation(DriveTrainTalonFX logan) {
			return logan.getPosition();
		}

	}
}
