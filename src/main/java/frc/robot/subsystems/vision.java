package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
//import frc.robot.subsystems.Swerve;
//import frc.robot.subsystems.arm;

public class vision extends SubsystemBase {
    
    private NetworkTable table;
    private double horizontalOffset0 = 0;
    private double verticalOffset0 = 0;
    private boolean aprilTag1Detected = false;

    public void armAutoUp() {}

    public vision() {
        // NetworkTable used to communicate with the LimeLight Camera
        table = NetworkTableInstance.getDefault().getTable("limelight"); 
    }
    
    // Function run periodically every 20ms
    public void periodic() {

        int selectedPipelines[] = {0, 1};

        for (int pipeline : selectedPipelines) {
            table.getEntry("pipeline").setNumber(pipeline);

            // Retrieve AprilTag data from selected pipeline
            NetworkTableEntry tx = table.getEntry("tx"); // Horizontal offset from crosshair to target (-27 degrees to 27 degrees)
            NetworkTableEntry ty = table.getEntry("ty"); // Vertical offset from crosshair to target (-20.5 degrees to 20.5 degrees)
            NetworkTableEntry tid = table.getEntry("tid");

            // Periodically read the data from the pipeline
            double horizontalOffset = tx.getDouble(0);
            double verticalOffset = ty.getDouble(0);
            int targetID = (int) tid.getDouble(0);

            updateSmartDashboard(horizontalOffset, verticalOffset, pipeline, targetID);

            if (pipeline == 0) {
                horizontalOffset0 = horizontalOffset;
                verticalOffset0 = verticalOffset;

                if (targetID == 1) {
                    aprilTag1Detected = true;
                }
            } else if (pipeline == 1 && targetID == 2 && aprilTag1Detected) {
                // Both pipelines (0 & 1) detect their respective AprilTags (1 & 2)
                double horizontalDistance = horizontalOffset - horizontalOffset;
                double verticalDistance = verticalOffset - verticalOffset;

                // Calculate for the hypotenus (closest distance) of both tags
                double distanceBetweenTags = Math.sqrt(Math.pow(horizontalDistance, 2) + Math.pow(verticalDistance, 2));
                SmartDashboard.putNumber("Distance Between AprilTags", distanceBetweenTags);
            }
        }
    }

    private void updateSmartDashboard(double horizontalOffset, double verticalOffset, int pipeline, int targetID) {   
        String prefix = "Pipeline #" + pipeline + "/AprilTag #" + targetID + " - ";
        
        SmartDashboard.putNumber(prefix + "Horizontal Offset", horizontalOffset);
        SmartDashboard.putNumber(prefix + "Vertical Offset", verticalOffset);
    }
}

