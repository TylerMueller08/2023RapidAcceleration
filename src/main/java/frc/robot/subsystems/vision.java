package frc.robot.subsystems;

//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
//import frc.robot.subsystems.Swerve;
//import frc.robot.subsystems.arm;

public class vision extends SubsystemBase {
    
    private NetworkTable table;

    public void armAutoUp() {}

    public vision() {
        // NetworkTable used to communicate with the LimeLight Camera
        table = NetworkTableInstance.getDefault().getTable("limelight"); 
    }
    
    // Function run periodically every 20ms
    public void periodic() {

        // Select pipeline to enable
        int selectedPipeline = 0;
        table.getEntry("pipeline").setNumber(selectedPipeline);

        // Force on led lights
        table.getEntry("ledMode").setNumber(3);

        // Retrieve AprilTag data from selected pipeline
        NetworkTableEntry tx = table.getEntry("tx"); // Horizontal offset from crosshair to target (-27 degrees to 27 degrees)
        NetworkTableEntry ty = table.getEntry("ty"); // Vertical offset from crosshair to target (-20.5 degrees to 20.5 degrees)
        NetworkTableEntry ta = table.getEntry("ta"); // Target area (0% of image to 100% of image)

        // Get the target's (AprilTag) ID from the pipeline
        NetworkTableEntry targetAprilTagID = table.getEntry("tid");
        int targetID = (int) targetAprilTagID.getDouble(0);

        // Periodically read the data from the pipeline
        double horizontalOffset = tx.getDouble(0);
        double verticalOffset = ty.getDouble(0);
        double targetArea = ta.getDouble(0);


        if (targetID == selectedPipeline && selectedPipeline != 0) {
            System.out.println("Current detecting AprilTag ID #" + targetID + " on pipeline #" + selectedPipeline);
            updateSmartDashboard(horizontalOffset, verticalOffset, targetArea);
        }
    }

    private void updateSmartDashboard(double horizontalOffset, double verticalOffset, double targetArea) {    
        //SmartDashboard.putNumber("Horizontal Offset", horizontalOffset);
        //SmartDashboard.putNumber("Vertical Offset", verticalOffset);
        //SmartDashboard.putNumber("Target Area", targetArea);

        System.out.println("Horizontal Offset: " + horizontalOffset);
        System.out.println("Vertical Offset: " + verticalOffset);
        System.out.println("Target Area: " + targetArea);
    }
}

