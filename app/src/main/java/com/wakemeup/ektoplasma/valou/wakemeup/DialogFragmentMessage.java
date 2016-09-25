package com.wakemeup.ektoplasma.valou.wakemeup;

/**
 * Created by Valentin on 25/09/2016.
 */
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class DialogFragmentMessage extends DialogFragment {

   /* @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.send_message, container,
                    false);
            //getDialog().setTitle("Envoyer un message avec le réveil");
            return rootView;
        }
*/
   @Override
   public Dialog onCreateDialog(Bundle savedInstanceState) {
       AlertDialog.Builder createProjectAlert = new AlertDialog.Builder(getActivity());

       createProjectAlert.setTitle("Voulez-vous envoyer un message ?");

       LayoutInflater inflater = getActivity().getLayoutInflater();

       createProjectAlert.setView(inflater.inflate(R.layout.send_message, null))

               .setNegativeButton(R.string.notsend, new DialogInterface.OnClickListener() {

                   @Override
                   public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                   }
               })

                .setPositiveButton(R.string.send, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText message = (EditText) getDialog().findViewById(R.id.message);
                        System.out.println("Le beau message -> " + message.getText());
                    }
                });

       return createProjectAlert.create();

   }
    public static DialogFragmentMessage newInstance() {
        DialogFragmentMessage f = new DialogFragmentMessage();
        return f;
    }
}
