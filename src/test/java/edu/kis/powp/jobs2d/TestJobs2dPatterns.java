package edu.kis.powp.jobs2d;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.kis.legacy.drawer.panel.DefaultDrawerFrame;
import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.drawer.DrawerFactory;
import edu.kis.powp.jobs2d.drivers.adapter.DrawerAdapter;
import edu.kis.powp.jobs2d.drivers.adapter.LineDrawerAdapter;
import edu.kis.powp.jobs2d.events.SelectChangeVisibleOptionListener;
import edu.kis.powp.jobs2d.events.SelectTestFigureOptionListener;
import edu.kis.powp.jobs2d.features.DrawerFeature;
import edu.kis.powp.jobs2d.features.DriverFeature;
import edu.kis.powp.jobs2d.features.LineFeature;

public class TestJobs2dPatterns {
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/**
	 * Setup test concerning preset figures in context.
	 * 
	 * @param application Application context.
	 */
	private static void setupPresetTests(Application application) {
		SelectTestFigureOptionListener figureJoeOneListener = new SelectTestFigureOptionListener(
				DriverFeature.getDriverManager(), DrawerFactory.getBoxDrawer());
		SelectTestFigureOptionListener figureJoeTwoListener = new SelectTestFigureOptionListener(
				DriverFeature.getDriverManager(), DrawerFactory.getDoodleDrawer());
		SelectTestFigureOptionListener figureJaneListener = new SelectTestFigureOptionListener(
				DriverFeature.getDriverManager(), DrawerFactory.getJaneDrawer());
		SelectTestFigureOptionListener squareListener = new SelectTestFigureOptionListener(
				DriverFeature.getDriverManager(), DrawerFactory.getSquareDrawer());
		SelectTestFigureOptionListener triangleListener = new SelectTestFigureOptionListener(
				DriverFeature.getDriverManager(), DrawerFactory.getTriangleDrawer());

		application.addTest("Figure Joe 1", figureJoeOneListener);
		application.addTest("Figure Joe 2", figureJoeTwoListener);
		application.addTest("Figure Jane", figureJaneListener);
		application.addTest("Square command", squareListener);
		application.addTest("Triangle command", triangleListener);
	}

	/**
	 * Setup driver manager, and set default driver for application.
	 * 
	 * @param application Application context.
	 */
	private static void setupDrivers(Application application) {
		DrawPanelController controller = DrawerFeature.getDrawerController();

		Job2dDriver loggerDriver = new LoggerDriver();
		DriverFeature.addDriver("Logger Driver", loggerDriver);
		DriverFeature.getDriverManager().setCurrentDriver(loggerDriver);

		Job2dDriver buggyDriver = new DrawerAdapter(controller);
		DriverFeature.addDriver("Buggy Simulator", buggyDriver);

		Job2dDriver lineDriver = new LineDrawerAdapter(controller);
		DriverFeature.addDriver("Superduper Simulator", lineDriver);

		DriverFeature.updateDriverInfo();
	}

	/**
	 * Auxiliary routines to enable using Buggy Simulator.
	 * 
	 * @param application Application context.
	 */
	private static void setupDefaultDrawerVisibilityManagement(Application application) {
		DefaultDrawerFrame defaultDrawerWindow = DefaultDrawerFrame.getDefaultDrawerFrame();
		application.addComponentMenuElementWithCheckBox(DrawPanelController.class, "Default Drawer Visibility",
				new SelectChangeVisibleOptionListener(defaultDrawerWindow), false);
		defaultDrawerWindow.setVisible(false);
	}

	/**
	 * Setup menu for adjusting logging settings.
	 * 
	 * @param application Application context.
	 */
	private static void setupLogger(Application application) {
		application.addComponentMenu(Logger.class, "Logger", 0);
		application.addComponentMenuElement(Logger.class, "Clear log",
				(ActionEvent e) -> application.flushLoggerOutput());
		application.addComponentMenuElement(Logger.class, "Fine level", (ActionEvent e) -> logger.setLevel(Level.FINE));
		application.addComponentMenuElement(Logger.class, "Info level", (ActionEvent e) -> logger.setLevel(Level.INFO));
		application.addComponentMenuElement(Logger.class, "Warning level",
				(ActionEvent e) -> logger.setLevel(Level.WARNING));
		application.addComponentMenuElement(Logger.class, "Severe level",
				(ActionEvent e) -> logger.setLevel(Level.SEVERE));
		application.addComponentMenuElement(Logger.class, "OFF logging", (ActionEvent e) -> logger.setLevel(Level.OFF));
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Application app = new Application("2d jobs Visio");
				DrawerFeature.setupDrawerPlugin(app);
				setupDefaultDrawerVisibilityManagement(app);

				DriverFeature.setupDriverPlugin(app);
				setupDrivers(app);
				setupPresetTests(app);
				setupLogger(app);
				LineFeature.setupLineMenu(app);

				app.setVisibility(true);
			}
		});
	}

}
