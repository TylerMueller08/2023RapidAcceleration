package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.subsystems.Swerve;

public class vision extends SubsystemBase {
    
    private final NetworkTable table;
    private final Swerve swerveSubsystem;

    public void armAutoUp() {}

    public vision(Swerve swerveSubsystem) {
        table = NetworkTableInstance.getDefault().getTable("limelight");
        this.swerveSubsystem = swerveSubsystem;
    }

    public void alignWithAprilTag() {
        // Get vertical offset of apriltag from crosshair (camera is rotated 90 degrees so horizontal and vertical offset are swapped)
        // NetworkTableEntry ty = table.getEntry("ty");
        // double verticalOffset = ty.getDouble(0);
        // Translation2 translation = new Translation2d(0.0, verticalOffset);
        
        Translation2d translation = new Translation2d(0.3, 0.0); // Move 1 foot forward (units in meters)
        double rotation = 0.0; // No rotation
        boolean fieldRelative = true; // Move relative to the field
        boolean isOpenLoop = false; // Closed-loop control

        swerveSubsystem.drive(translation, rotation, fieldRelative, isOpenLoop);
    }
    
    public void periodic() {
        int selectedPipeline = 2;

        table.getEntry("pipeline").setNumber(selectedPipeline);


        NetworkTableEntry tx = table.getEntry("tx");
        NetworkTableEntry ty = table.getEntry("ty");

        double horizontalOffset = tx.getDouble(0);
        double verticalOffset = ty.getDouble(0);

        SmartDashboard.putNumber("Horizontal Offset", horizontalOffset);
        SmartDashboard.putNumber("Vertical Offset", verticalOffset);

        alignWithAprilTag();
    }
}

