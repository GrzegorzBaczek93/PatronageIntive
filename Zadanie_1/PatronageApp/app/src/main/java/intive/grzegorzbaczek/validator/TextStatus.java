package intive.grzegorzbaczek.validator;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

public abstract class TextStatus implements TextWatcher {

    private final TextView textView;

    public TextStatus(TextView textView){
        this.textView = textView;
    }

    public abstract void validate(TextView textView, String text);

    @Override
    public void afterTextChanged(Editable editable) {
        String text = textView.getText().toString();
        validate(textView, text);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        /* Don't care */
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        /* Don't care */
    }
}
