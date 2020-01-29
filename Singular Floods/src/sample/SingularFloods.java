package sample;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SingularFloods{

    private int raiseSize = 60;                          // first for
    private int decreaseSize = 50;                       // second for

    // I N P U T    D A T A
    private double floodLifeTime;                   // T9
    private double raiseTime;                       // T0
    private double maxFlow;                         // Q1
    private double gamma;                           // G0
    private double step;                            // T

    // O U P U T    DATA
    private LinkedHashMap< Double, Double > flowValues = new LinkedHashMap<>(  );

    // O T H E R    D A T A
    private double maxVolume;
    private double dimensionlessVolume;
    private double dimensionlessTime;
    private double fi;
    private double squareRootBase;
    private double squareRoot;
    private double firstArgument;
    private double secondArgument;
    private double thirdArgument;
    private double firstCalculatedVolume;
    private double secondCalculatedVolume;
    private double firstStep;
    private double secondStep;
    private double savedFirstCalculatedVolume;
    private double savedSecondCalculatedVolume;
    private double decreaseStep;
    private double A;
    private double B;
    private double C;
    private double floodVolume;
    private boolean calculateFlows = false;


    public SingularFloods( double floodLifeTime, double raiseTime, double maxFlow, double gamma, double step, int raiseIterNr, int decreaseIterNr ){
        this.floodLifeTime = floodLifeTime;
        this.raiseTime = raiseTime;
        this.maxFlow = maxFlow;
        this.gamma = gamma;
        this.step = step;
        this.raiseSize = raiseIterNr;
        this.decreaseSize = decreaseIterNr;
    }

    public void calculateSingularFloods(){
        // maxVolume = W
        maxVolume = gamma * maxFlow * floodLifeTime;

        // dimensionlessTime = T1
        dimensionlessTime = floodLifeTime / raiseTime;

        // dimensionlessVolume = W1
        dimensionlessVolume = gamma * dimensionlessTime;

        // fi(R1 - 1)
        fi = dimensionlessTime - 1;

        calculateFirstIteration();
        if( !calculateFlows ){
            squareRootBase = 0;
            calculateSecondIteration();
        }
    }

    public double getMaxVolume(){
        return maxVolume;
    }

    public double getFloodVolume(){
        return floodVolume;
    }

    private double calculateSquareRootBase( double step ){
        return 4 * step * fi - ( fi - 1 ) * ( fi - 1 );
    }

    private void calculateFirstArgument( double step ){
        firstArgument = ( ( step + 1 ) * ( ( fi - 1 ) * Math.log( fi ) ) ) / ( step * step );
    }

    private void calculateSecondArgument( double step ){
        //secondArgument = ( ( step + 1 ) * ( squareRootBase - 2 * step * fi ) ) / ( (step * step) * squareRoot );
        secondArgument = ( ( step + 1 ) * ( 2 * step * fi - ( ( fi - 1 ) * ( fi - 1 ) ) ) ) / ( (step * step) * squareRoot );
    }

    private void calculateThirdArgument( double step ){
        thirdArgument = Math.atan( ( 2 * step * fi + ( fi - 1 ) ) / squareRoot ) - Math.atan( ( fi - 1 - 2 * step ) / squareRoot );
    }

    private void calculateFirstIteration(){
        for( int i = 1; i <= raiseSize; i++ ){
            squareRootBase = calculateSquareRootBase( i );
            if( squareRootBase >= 2.0 ){
                squareRoot = Math.sqrt( squareRootBase );
                calculateFirstArgument( i );
                calculateSecondArgument( i );
                calculateThirdArgument( i );
                firstCalculatedVolume = firstArgument + secondArgument * thirdArgument - dimensionlessTime/i;
                if( firstCalculatedVolume == dimensionlessVolume ){
                    A = i;
                    calculateFlowValues();
                    calculateFlows = true;
                    return;
                }
                if( firstCalculatedVolume > dimensionlessVolume ){
                    firstStep = i;
                    savedFirstCalculatedVolume = firstCalculatedVolume;
                }
            }
        }
    }

    private void calculateSecondIteration(){
        for( int j = 1; j <= decreaseSize; j++ ){
            decreaseStep = firstStep + 0.01 * j;
            squareRootBase = calculateSquareRootBase( decreaseStep );
            squareRoot = Math.sqrt( squareRootBase );
            calculateFirstArgument( decreaseStep );
            calculateSecondArgument( decreaseStep );
            calculateThirdArgument( decreaseStep );
            secondCalculatedVolume = firstArgument + secondArgument * thirdArgument - dimensionlessTime/decreaseStep;

            if( secondCalculatedVolume == dimensionlessVolume ){
                A = decreaseStep;
            }
            if( secondCalculatedVolume > dimensionlessVolume ){
                secondStep = decreaseStep;
                savedSecondCalculatedVolume = secondCalculatedVolume;
                //continue;
            }
            if( j != 1 ){
                A = secondStep + ( ( decreaseStep - secondStep  ) * ( savedSecondCalculatedVolume - dimensionlessVolume ) ) / ( savedSecondCalculatedVolume - secondCalculatedVolume );
                calculateFlowValues();
                return;
            }
            A = firstStep + ( ( decreaseStep - firstStep  ) * ( savedFirstCalculatedVolume - dimensionlessVolume ) ) / ( savedFirstCalculatedVolume - secondCalculatedVolume );
            calculateFlowValues();
            return;
        }
    }

    private void calculateFlowValues(){
        B = dimensionlessTime - 2 - 2 * A;
        C = A + 1;
        double step = 0;
        do{
            double floodFlow = calculateFlood( step );
            flowValues.put( step, floodFlow );
            floodVolume += floodFlow * this.step;
            step += this.step;
        }while( step <= floodLifeTime );
    }


    private double calculateFlood( double step ){
        return step * ( floodLifeTime - step ) / ( A * step * step + B * raiseTime * step + C * raiseTime * raiseTime ) * maxFlow;
    }

    public LinkedHashMap< Double, Double > getFlowValues(){
        return flowValues;
    }
}
