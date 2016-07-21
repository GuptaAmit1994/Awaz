package com.example.keshavdulal.a14_simple_drawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.io.IOException;

public class GraphFragment extends Fragment {

    public static float graph_height;
    static short[] recordAudioData = null;
    static short[] recordDecibelData = null ;
    static short[] playAudioData = null;
    static MainActivity mainActivity = new MainActivity();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.graph_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        MainActivity a= new MainActivity();
       // a.is
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.rect);
        linearLayout.addView(new myGraphView(getActivity()));
        // Log.d("VIVZ", "Linear Layout - "+linearLayout.getHeight());
    }

    public static class myGraphView extends View {
        public myGraphView(Context context) {

            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            //BACKGROUND
            canvas.drawColor(Color.LTGRAY);
            //Paint Object
            Paint graphBoundaryObj = new Paint();
            graphBoundaryObj.setColor(Color.parseColor("#880000"));
            graphBoundaryObj.setStrokeWidth(7);

            float X1 = 0;
            float Y1 = canvas.getHeight() / 2;
            float AX = canvas.getWidth();
            float AY = canvas.getHeight() / 2;
            float X2, Y2;
            drawMeshLines(canvas);

            //Midline - Divider
            //graphBoundaryObj.setStrokeWidth(2);
            //canvas.drawLine(X1, Y1, AX, AY, graphBoundaryObj);
            //Vertical graphical line
            Paint graphLinesObj = new Paint();        //GLO graph-lines-object
            graphLinesObj.setColor(Color.parseColor("#880000"));
            graphLinesObj.setStrokeWidth(1);

            if (recordAudioData == null) {
                int length = mainActivity.getRecordBufferSize();
                recordAudioData = new short[length];
            }
            if (recordDecibelData == null) {
                int length = mainActivity.getRecordBufferSize();
                recordDecibelData = new short[length];
            }
            if (playAudioData == null) {
                int length = mainActivity.getPlayBufferSize();
                length = length/4;
                playAudioData = new short[length];
            }
            int recordBuffIndex = (recordAudioData.length / 2 - canvas.getWidth()) / 2;
            //int playBuffIndex = (playAudioData.length / 2 - canvas.getWidth()) / 2;
            int i =0;
            Y1=0;
            if (MainActivity.playState() == 1) {
                Y1=0;
                for (X1 = 0; X1 <= canvas.getWidth(); X1++) {
                    graph_height = ((float) playAudioData[i]) / 50;
                    X2 = X1+1;
                    Y2 = 450- graph_height;
                    canvas.drawLine(X1, Y1, X2, Y2, graphLinesObj);
                    Y1=Y2;
                    //playBuffIndex++;
                    i++;
                }
                postInvalidateDelayed(1);
//                invalidate();
            } else {
                Y1=0;
               // int maxDecibel = recordDecibelData.
                for (X1 = 0; X1 <= canvas.getWidth(); X1++) {
                    graph_height = (recordDecibelData[recordBuffIndex]);
                    X2 = X1+1;
                   if((int)recordAudioData[recordBuffIndex]<0)
                       Y2 = canvas.getHeight()/2 + (40+graph_height);
                   else
                        Y2 = canvas.getHeight()/2 - (40-graph_height);

                    canvas.drawLine(X1, Y1, X2, Y2, graphLinesObj);
                    Y1=Y2;
                    recordBuffIndex++;
                }
                postInvalidateDelayed(1);
//                invalidate();
            }
        }

        public void drawMeshLines(Canvas canvas) {
            //Mesh Lines
            int cgh = canvas.getHeight();
            int cgw = canvas.getWidth();
            int cgh2 = cgh / 2;
            int cgw2 = cgw / 2;
            int meshDim = cgh2 / 20;
            int i;
            Paint meshObj = new Paint();
            meshObj.setColor(Color.parseColor("#808080"));
            meshObj.setStrokeWidth(1);

            //Horizontal Lines - Top Segment
            for (i = cgh2; i >= 0; i -= meshDim) {
                canvas.drawLine(0, i, cgw, i, meshObj);
            }
            //Horizontal Lines - Bottom Segment
            for (i = cgh2; i <= cgh; i += meshDim) {
                canvas.drawLine(0, i, cgw, i, meshObj);
            }

            //Vertical Lines - Left Segment
            for (i = cgw2; i >= 0; i -= meshDim) {
                canvas.drawLine(i, 0, i, cgh, meshObj);
            }
            //Vertical Lines - Right Segment
            for (i = cgw2; i <= cgw; i += meshDim) {
                canvas.drawLine(i, 0, i, cgh, meshObj);
            }
        }
    }

    //updating audiodata from mainactivity
    public void updateRecordGraph(short[] data,short[] data2) {
        recordAudioData = data;
        recordDecibelData= data2;
    }

    public void setRecordBufferSize(int size) {
        recordAudioData = new short[size];
    }

    public void updatePlayGraph(short[] data) {
        playAudioData = data;
    }

    public void setPlayBufferSize(int size) {
        playAudioData = new short[size];
    }
}