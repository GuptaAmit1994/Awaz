package com.awaj;

public class FrequencyValue {
   /* public static float getFundamentalFrequency (float[] amplitude){

        float fundamentalFrequency, maxAmplitude=0;
        float correctFactor = 1.082f;
        int i_max=0, length =amplitude.length/2;

        float[] harmonics1= downSample(amplitude,2);
        float[] harmonics2= downSample(amplitude,3);
        float[] sum=new float[amplitude.length];
        for(int i=0;i<length;i++){
            sum[i]=amplitude[i]*harmonics1[i]*harmonics2[i];

            if(sum[i]>maxAmplitude){
                maxAmplitude=sum[i];
                i_max=i;
            }

        }
        fundamentalFrequency= (i_max*MainActivity.getResolution())*correctFactor;
        return fundamentalFrequency;
    }

    public static float[] downSample(float[] input, int downSamplingRate){
        int N= input.length/2;
        float[] downSampledSignal = new float[N];
        for(int i=0;i<N;i++){
            if((i)*downSamplingRate<N) {
                downSampledSignal[i] = input[(i)*downSamplingRate];
            }
            else{
                downSampledSignal[i]=0;
            }
        }
        return downSampledSignal;

    }
*/

//}
    public static float getFundamentalFrequency(float[] amplitude) {
        float fundamentalFrequency=0;
        float correction_factor=1.082f;
        int i_max=0;
        float max=0;
        for(int i=0;i<amplitude.length/2;i++){
            if(max < amplitude[i]){
                max=amplitude[i];
                i_max =i;
            }
        }
        fundamentalFrequency= (i_max)*MainActivity.getResolution()*correction_factor;

        return fundamentalFrequency;
    }
}
