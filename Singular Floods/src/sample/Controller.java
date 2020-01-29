package sample;


import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;


import java.util.*;

public class Controller {

    SingularFloods2 singularFloods;

    @FXML
    private TextField floodLifeTimeTextField;

    @FXML
    private TextField raiseTimeTextField;

    @FXML
    private TextField maxFlowTextField;

    @FXML
    private TextField gammaTextField;

    @FXML
    private TextField stepSizeTextField;

    @FXML
    private LineChart< String, Number > hydrographChart;

    @FXML
    private TextField raiseIterTextField;

    @FXML
    private TextField decreaseIterTextField;

    @FXML
    private TextArea textArea;

    @FXML
    private void calculateSingularFloods(){
        Double floodLifeTime = doubleFromTextField( floodLifeTimeTextField );
        if( floodLifeTime == null ){
            showAlert( "Te rog seteaza durata totala a viituri!" );
            floodLifeTimeTextField.requestFocus();
            return;
        }

        Double raiseTime = doubleFromTextField( raiseTimeTextField );
        if( raiseTime == null ){
            showAlert( "Te rog seteaza durata de crestere a hidrografului!" );
            raiseTimeTextField.requestFocus();
            return;
        }


        Double maxFlow = doubleFromTextField( maxFlowTextField );
        if( maxFlow == null ){
            showAlert( "Te rog seteaza debitul maxim!" );
            maxFlowTextField.requestFocus();
            return;
        }

        Double gamma = doubleFromTextField( gammaTextField );
        if( gamma == null ){
            showAlert( "Te rog seteaza valoarea pentru gamma!" );
            gammaTextField.requestFocus();
            return;
        }
        if( gamma < 0 || gamma > 1 ){
            showAlert( "Gamma trebuie sa fie intre 0 and 1!" );
            gammaTextField.requestFocus();
            return;
        }

        Double stepSize = doubleFromTextField( stepSizeTextField );
        if( stepSize == null ){
            showAlert( "Te rog seteaza pasul de calcul!" );
            stepSizeTextField.requestFocus();
            return;
        }
        if( stepSize > floodLifeTime ){
            showAlert( "Pasul de calcul nu poate fi mai mare decat durata totala a viituri!" );
            stepSizeTextField.requestFocus();
            return;
        }

        Double raiseSize = doubleFromTextField( raiseIterTextField );
        if( raiseSize == null ){
            showAlert( "Te rog seteaza numarul de iteratii pentru panta de crestere!" );
            stepSizeTextField.requestFocus();
            return;
        }
        int raiseSizeNr = raiseSize.intValue();

        Double decreaseSize = doubleFromTextField( decreaseIterTextField );
        if( decreaseSize == null ){
            showAlert( "Te rog seteaza numarul de iteratii pentru panta de descrestere!" );
            stepSizeTextField.requestFocus();
            return;
        }
        int decreaseSizeNr = decreaseSize.intValue();
        singularFloods = new SingularFloods2( floodLifeTime, raiseTime, maxFlow, gamma, stepSize, raiseSizeNr, decreaseSizeNr );
        singularFloods.calculateSingularFloods();

        setLineChartData(  singularFloods.getFlowValues() );
    }


    private Double doubleFromTextField( TextField tf ){
        String text = tf.getText();
        if( text.equals( "" ) ){
            return null;
        }
        Double doubleValue = null;
        try{
            doubleValue = Double.valueOf( text );
        }catch( NumberFormatException e ){
            showAlert(  "Characters are not allowed" );
            tf.requestFocus();
        }
        return doubleValue;
    }

    private void showAlert( String message ){
        Alert alert = new Alert( Alert.AlertType.INFORMATION);
        alert.setTitle( "Ups..." );
        alert.setHeaderText( null );
        alert.setContentText( message );

        alert.showAndWait();
    }

    private void setLineChartData( LinkedHashMap< Double, Double> data ){
        XYChart.Series< String, Number > series = new XYChart.Series<>();
        StringBuilder stringBuilder = new StringBuilder(  );
        stringBuilder.append( "W max: " + singularFloods.getMaxVolume() );
        stringBuilder.append( "\n" );
        stringBuilder.append( "W calculat: " + String.format("%1.2f", singularFloods.getFloodVolume() ) );
        stringBuilder.append( "\n" );
        stringBuilder.append( "\n" );
        stringBuilder.append( "Timp" + "\t" + "Debit" );
        stringBuilder.append( "\n" );
        for( Double key : data.keySet() ){
            Double value = data.get( key );
            series.getData().add( new XYChart.Data<>( String.valueOf( key ), Double.valueOf(  String.format("%.2f", value) ) ) );
            stringBuilder.append( key + "\t" + String.format("%.2f", value ) );
            stringBuilder.append( "\n" );
        }
        textArea.setText( stringBuilder.toString() );
        series.setName( "Hidrograf" );
        if( hydrographChart.getData() != null ){
            hydrographChart.getData().clear();
        }

        hydrographChart.getData().add( series );


    }

}

