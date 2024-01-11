package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
//import frc.robot.subsystems.Swerve;
//import frc.robot.subsystems.arm;

public class vision extends SubsystemBase {
    
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight"); // table used to communicate with the limelight camera

    // Setup a pipeline
    NetworkTableEntry tx = table.getEntry("tx"); // Horizontal offset from crosshair to target (-27 degrees to 27 degrees)
    NetworkTableEntry ty = table.getEntry("ty"); // Vertical offset from crosshair to target (-20.5 degrees to 20.5 degrees)
    NetworkTableEntry ta = table.getEntry("ta"); // Target area (0% of image to 100% of image)
    NetworkTableEntry tv = table.getEntry("tv"); // Whether the limelight has any valid targets (0 or 1)

    private int activePipeline = 0; // Set the active pipeline
    setPipeline(activePipeline); // Set the default pipeline number
    
    public void setPipeline(int pipeline) {
        // Actually set the active pipeline
        table.getEntry("pipeline").setNumber(pipeline);
        activePipeline = pipeline;
    }

    // returns the value of the pipeline
    double horizontalOffset = tx.getDouble(0);
    double verticalOffset = ty.getDouble(0);
    double percentArea = ta.getDouble(0);

    
    public void periodic () {
        SmartDashboard.putBoolean();
    }
}

