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

    // Setup pipeline 0 for apriltag id 1
    NetworkTableEntry tx1 = table.getEntry("tx0"); // Horizontal offset from crosshair to target (-27 degrees to 27 degrees)
    NetworkTableEntry ty1 = table.getEntry("ty0"); // Vertical offset from crosshair to target (-20.5 degrees to 20.5 degrees)
    NetworkTableEntry ta1 = table.getEntry("ta0"); // Target area (0% of image to 100% of image)
    NetworkTableEntry tv1 = table.getEntry("tv0"); // Whether the limelight has any valid targets (0 or 1)

    // set pipeline 1 for aptril tag id 2
    NetworkTableEntry tx2 = table.getEntry("tx1");
    NetworkTableEntry ty2 = table.getEntry("ty1");
    NetworkTableEntry ta2 = table.getEntry("ta1");
    NetworkTableEntry tv2 = table.getEntry("tv1");

    // repeat or is there a more efficient way?

    private int activePipeline = 0; // Set the active pipeline
    setPipeline(activePipeline); // Set the default pipeline number
    
    public void setPipeline(int pipeline) {
        // Actually set the active pipeline
        table.getEntry("pipeline").setNumber(pipeline);
        activePipeline = pipeline;
    }

    // returns the value of each for the currently active pipeline
    public double horizontalOffset () { return table.getEntry("tx" + activePipeline).getDouble(0); }
    public double verticalOffset () { return table.getEntry("ty" + activePipeline).getDouble(0); }
    public double percentArea () { return table.getEntry("ta" + activePipeline).getDouble(0); }
    public boolean isTarget () { return table.getEntry("tv" + activePipeline).getDouble(0) == 1.0; }
    
    public boolean weGreen () { return horizontalOffset() < 5 && horizontalOffset() > -5 && isTarget() == 1; } // if horizontal offset is within a range and if there is a valid target idk what this is
    
    public void periodic () {
        SmartDashboard.putBoolean("Is target ", weGreen());
    }
}

