package comp3350.goodhabits.Logic;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

//Этот класс используется для создания средства выбора времени, которое используется для приема входных данных "Time"
public class TimePickerFragment extends DialogFragment {
    @NonNull
    @Override
    //Эта функция предлагает пользователю выбрать желаемое время, по умолчанию установлено текущее время
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();    // Календарь используется для отсчета текущего времени
        int hour = c.get(Calendar.HOUR_OF_DAY);    // Текущий час
        int minute = c.get(Calendar.MINUTE);    // Текущая минута

        // Возвращает время, выбранное пользователем, показывает текущее время в качестве начального
        return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) getActivity(), hour,minute, DateFormat.is24HourFormat(getActivity()));
    }
}
