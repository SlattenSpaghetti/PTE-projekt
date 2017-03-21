package presentation;

import exceptions.ArealEjDefineretException;
import exceptions.DimensionerendeKraftEjDefineretException;
import exceptions.ForskydningsspaendingEjDefineretException;
import exceptions.NormalkraftEjDefineretException;
import exceptions.NormalspaendingEjDefineretException;
import exceptions.TvaerkraftEjDefineretException;
import exceptions.VinkelEjDefineretException;
import exceptions.erUnderFejlgraenseException;
import javafx.scene.control.TextField;
import logic.PTECalculatorController;
import logic.PTECalculatorControllerImpl;
import observers.AngleObserver;

public class HorizontalAngleTextField extends TextField {
	public HorizontalAngleTextField() {
		this.setPromptText("Vandret vinkel");
		this.setMaxSize(150, 20);
		this.setOnKeyReleased(e -> {
			try {
				if(!this.getText().isEmpty())
				notifyObservers();
				else{
					FrontPage.frontPageMediator.getVerticalAngleText().setDisable(false);
					FrontPage.frontPageMediator.getVerticalAngleText().setText("");
					FrontPage.frontPageMediator.frontPageTopLeft.getTriangle().getChildren().setAll(new NeedMoreInputTriangle());
				}
			} catch (DimensionerendeKraftEjDefineretException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}

	private void notifyObservers() throws DimensionerendeKraftEjDefineretException {
		new AngleObserver().update(this.getText(), this);
		
		
		try {
			FrontPage.frontPageMediator.getObserver().getPteCalc().angivVinkel(Double.parseDouble(this.getText()), true);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (erUnderFejlgraenseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			FrontPage.frontPageMediator.getObserver().getPteCalc().beregnNormalkraft();
		} catch (DimensionerendeKraftEjDefineretException | VinkelEjDefineretException e2) {
			// Gør ingen ting
		}
		try {
			FrontPage.frontPageMediator.getObserver().getPteCalc().beregnTvaerkraft();
		} catch (DimensionerendeKraftEjDefineretException | VinkelEjDefineretException e2) {
			// Gør ingen ting
		}

		try {
			FrontPage.frontPageMediator.getObserver().getPteCalc().beregnForskydningsspaendning();
		} catch (DimensionerendeKraftEjDefineretException | VinkelEjDefineretException
				| ForskydningsspaendingEjDefineretException | ArealEjDefineretException
				| TvaerkraftEjDefineretException e1) {
			// Gør ingen ting
		}
		
		try {
			FrontPage.frontPageMediator.getObserver().getPteCalc().beregnNormalspaending();
		} catch (NormalkraftEjDefineretException | DimensionerendeKraftEjDefineretException | VinkelEjDefineretException | NormalspaendingEjDefineretException | ArealEjDefineretException e1) {
			// gør ingen ting
		}
	}
}
