package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class vision extends SubsystemBase {
    
    private NetworkTable table;
    // private int currentPipeline = 0;

    public void armAutoUp() {}

    public vision() {
        table = NetworkTableInstance.getDefault().getTable("limelight"); 
    }
    
    public void periodic() {
        int selectedPipelines[] = {0, 1};

        for (int pipeline : selectedPipelines) {
            table.getEntry("pipeline").setNumber(pipeline);

            // Allow for limelight to switch between pipelines before getting data
            // Wait for camera to switch before updated data becomes available
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            NetworkTableEntry tx = table.getEntry("tx");
            NetworkTableEntry ty = table.getEntry("ty");

            double horizontalOffset = tx.getDouble(0);
            double verticalOffset = ty.getDouble(0);

            if (pipeline == 0) {
                SmartDashboard.putNumber("P0 - HO", horizontalOffset);
                SmartDashboard.putNumber("P0 - VO", verticalOffset);
            } else if (pipeline == 1) {
                SmartDashboard.putNumber("P1 - HO", horizontalOffset);
                SmartDashboard.putNumber("P1 - VO", verticalOffset);
            }
        }

        // IF the previously mentioned code doesn't work with delaying, try this
        // Switches pipelines back and forth after each periodic call
        // table.getEntry("pipeline").setNumber(currentPipeline);

        // NetworkTableEntry tx = table.getEntry("tx");
        // NetworkTableEntry ty = table.getEntry("ty");

        // double horizontalOffset = tx.getDouble(0);
        // double verticalOffset = ty.getDouble(0);

        // SmartDashboard.putNumber(currentPipeline + "-HO", horizontalOffset);
        // SmartDashboard.putNumber(currentPipeline + "-VO", verticalOffset);

        // // Switch to next pipeline for next time periodic() is called
        // currentPipeline = (currentPipeline + 1) % 2;
    }
}

