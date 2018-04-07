package com.frangoudes.coffeeorder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int nbrOfCoffees = 2;
    int pricePerCup = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayQ(nbrOfCoffees);
    }

    public void incQtyBtn(View view) {
        if (nbrOfCoffees > 9) {
            Toast.makeText(this, "Maximum order 10 coffees!",
                    Toast.LENGTH_SHORT).show();
        } else {
            nbrOfCoffees = nbrOfCoffees + 1;
            displayQ(nbrOfCoffees);
            displayP(calculateP(nbrOfCoffees, pricePerCup));
        }
    }

    public void decQtyBtn(View view) {
        if (nbrOfCoffees < 2) {
            Toast.makeText(this, "Minimum order 1 coffee!",
                    Toast.LENGTH_SHORT).show();
        } else {
            nbrOfCoffees = nbrOfCoffees - 1;
            displayQ(nbrOfCoffees);
            displayP(calculateP(nbrOfCoffees, pricePerCup));
        }
    }

    public void orderBtn(View view) {
        EditText etName = (EditText) findViewById(R.id.et_name);
        String name = etName.getText().toString();
        TextView priceTextView = (TextView) findViewById(R.id.tv_order_summary);
        int totalPrice = calculateP(nbrOfCoffees, pricePerCup);
        String orderSummeryMsg = createOrderMsg(name, nbrOfCoffees, totalPrice);

//        TextView orderSummery = (TextView) findViewById(R.id.tv_order_summary);
//        orderSummery.setText(orderSummeryMsg);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee Order");
        intent.putExtra(Intent.EXTRA_TEXT, orderSummeryMsg);
        Log.i("MainActivity", "Intent prepared");
        if (intent.resolveActivity(getPackageManager()) != null) {
            Log.i("MainActivity", "Sending Intent");
            startActivity(intent);
        } else {
            Log.i("MainActivity", "Intent not sent");
        }
    }

    void displayP(int p) {
        TextView textView = (TextView) findViewById(R.id.tv_order_summary);
        textView.setText("Price: " + p);
    }

    void displayQ(int q) {
        TextView textView = (TextView) findViewById(R.id.tv_q);
        textView.setText("" + q);
    }

    /**
     * This method displays the given text on the screen.
     */
    private String createOrderMsg(String name, int q, int tp) {
        CheckBox whippedCream = (CheckBox) findViewById(R.id.cb_whippedCream);
        boolean whippedCreamChecked = whippedCream.isChecked();
        return ("Name: " + name + "\n" +
                "Add whipped cream? " + whippedCreamChecked + "\n" +
                "Coffees Ordered: " + q + "\n" +
                "Total Cost: " + "$" + tp);
    }

    private int calculateP(int q, int p) {
        return q * p;
    }

    ;
}
