package com.example.salon.turnos;

//import android.app.DatePickerDialog;
//import android.app.TimePickerDialog;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.salon.Cliente;
import com.example.salon.R;
import com.example.salon.Turno;
import com.example.salon.adapters.AdapterTurnosU;
import com.example.salon.database.clsWebRequest;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.Timepoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentTurnos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTurnos extends Fragment implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener, AdapterTurnosU.OnNoteListener {

    private Button bReserva,bAceptar,bagregar;
    private Calendar calendar,miCalendar;
    private int year, month, day, hour, minute,diaTurno;
    private View view;
    private Dialog dialog;
    private TextView tvCerrar,tvFecha,tvHora,tvAviso,tvServicio;
    private RecyclerView rv;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    Context context;
    Activity activity;
    private ArrayList<Turno> listaTurno = new ArrayList<>();
    private String fechaTurno,horaTurno, idU,horaBD,fechBD,servicioBD,servicioNom,idTipoUsuario,nom,ape,dni,email;
    FragmentTurnos frag;

    public SharedPreferences sharedPreferences;
    public String SHARED_PREF_NAME ="user_pref";
    public SharedPreferences.Editor sharedPrefEditor;

    public FragmentTurnos() {
        // Required empty public constructor
    }

    public static FragmentTurnos newInstance() {
        FragmentTurnos fragment = new FragmentTurnos();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.frag = this;
        init();
        this.idU = sharedPreferences.getString("id","");
        this.idTipoUsuario = sharedPreferences.getString("tipoUsuarioId","");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_turnos, container, false);
        crearFechas();
        armarPopUp();
        this.bReserva = (Button) this.view.findViewById(R.id.botonReserva);
        if (idTipoUsuario.equals("1")){
            this.bReserva.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogDatos();
                }
            });
        } else {
            this.bReserva.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vistaCalendario();
                }
            });
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //init();
        this.miCalendar = Calendar.getInstance();
        this.tvAviso = (TextView) this.view.findViewById(R.id.tvSinTurno);
        String emailU = sharedPreferences.getString("email","");
        //this.idU = sharedPreferences.getString("id","");
        //this.idTipoUsuario = sharedPreferences.getString("tipoUsuarioId","");
        /*if (idTipoUsuario.equals("1")){
            this.bReserva.setVisibility(View.GONE);
        }*/
        RequestParams param = new RequestParams();
        param.add("type","getTurno");
        param.add("tipoUsuarioId",idTipoUsuario);
        param.add("emailT",emailU);
        //param.add("idUsuario","1");
        clsWebRequest.post(context,"apiSalon.php",param,new FragmentTurnos.ResponseHandler());

    }

    public void init(){
        this.activity = getActivity();
        this.context = getContext();
        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        sharedPrefEditor = sharedPreferences.edit();
    }



    public void armarPopUp(){
        this.dialog = new Dialog(requireContext());
        this.dialog.setContentView(R.layout.popup_turno);
        this.dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        this.tvFecha = (TextView) this.dialog.findViewById(R.id.TVFechaTurno);
        this.tvHora = (TextView) this.dialog.findViewById(R.id.TVHoraTurno);
        this.tvServicio = (TextView) this.dialog.findViewById(R.id.TVServiTurno);
    }

    public void dialogDatos(){
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Datos");
        final EditText input,inputA,inputdni,inputmail;
        input = new EditText(context);
        inputA = new EditText(context);
        inputdni = new EditText(context);
        inputmail = new EditText(context);
        input.setHint("Nombre");
        inputA.setHint("Apellido");
        inputdni.setHint("DNI");
        inputmail.setHint("Email");
        layout.addView(input);
        layout.addView(inputA);
        layout.addView(inputdni);
        layout.addView(inputmail);

        builder.setView(layout);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nom = input.getText().toString();
                ape = inputA.getText().toString();
                dni = inputdni.getText().toString();
                email = inputmail.getText().toString();
                //crearTurnoAdmin(nom,ape,dni);
                vistaCalendario();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }



    public void crearFechas(){
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR) ;
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
    }

    public void vistaCalendario() {
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(FragmentTurnos.this,  //Your Fragment Class Name should be here, not getActivity(), or getContex().
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show(getActivity().getFragmentManager(), "DatePicker");
        Calendar min_date_c = Calendar.getInstance();
        datePickerDialog.setMinDate(min_date_c);
        // Setear fecha a 2 semanas
        Calendar max_date_c = Calendar.getInstance();
        max_date_c.set(Calendar.DAY_OF_MONTH, day + 14);
        datePickerDialog.setMaxDate(max_date_c);
        //Disable all SUNDAYS and SATURDAYS between Min and Max Dates
        for (Calendar loopdate = min_date_c; min_date_c.before(max_date_c); min_date_c.add(Calendar.DATE, 1), loopdate = min_date_c) {
            int dayOfWeek = loopdate.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY) {
                Calendar[] disabledDays = new Calendar[1];
                disabledDays[0] = loopdate;
                datePickerDialog.setDisabledDays(disabledDays);
            }

        }
    }

    public void vistaReloj(Timepoint[] timepoints){
        int horaMin;
        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(FragmentTurnos.this,hour,minute,true);
        timePickerDialog.setThemeDark(false);
        timePickerDialog.enableMinutes(false);
        if (this.diaTurno == this.day){
            horaMin = hour+1;
        } else {
            horaMin = 10;
        }
        timePickerDialog.setMinTime(horaMin,minute, 0);
        timePickerDialog.setMaxTime(19,0,0);
        //Desabilitar horas
        timePickerDialog.setDisabledTimes(timepoints);
        timePickerDialog.show(getActivity().getFragmentManager(),"Reloj");
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        int mes = monthOfYear + 1;
        this.diaTurno = dayOfMonth;
        this.fechaTurno = dayOfMonth + "/" + mes + "/" + year;
        this.fechBD = year + "-" + mes + "-" + dayOfMonth;
        this.tvFecha.setText(fechaTurno);
        this.miCalendar.set(year,monthOfYear,dayOfMonth);
        RequestParams param = new RequestParams();
        param.add("type","getHorasOcupadas");
        param.add("fecha",fechBD);
        clsWebRequest.post(context,"apiSalon.php",param,new FragmentTurnos.ResponseHora());


    }

    public void pedirServicio(){
        final String[] serv = {"Corte","Teñido","Alisado/Ondulado","Permanente","Baño de Crema"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Elija un Servicio");
        builder.setItems(serv, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the user clicked on colors[which]
                servicioBD = Integer.toString(which + 1);
                servicioNom = serv[which];
                tvServicio.setText(servicioNom);
                popUpConfirm();
            }
        });
        builder.show();
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        this.horaTurno = hourOfDay + ":00";
        this.horaBD = hourOfDay + ":00:00";
        this.tvHora.setText(horaTurno);
        this.miCalendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        this.miCalendar.set(Calendar.MINUTE,0);
        //popUpConfirm();
        pedirServicio();
    }

    public void popUpConfirm(){
        this.tvCerrar = (TextView) this.dialog.findViewById(R.id.XCerrar);
        this.bagregar = (Button) this.dialog.findViewById(R.id.bAgregarCal);
        this.bAceptar = (Button) this.dialog.findViewById(R.id.bAceptarTurno);
        if (idTipoUsuario.equals("1")){
            this.bagregar.setVisibility(View.GONE);
            this.bAceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    crearTurnoAdmin();
                    dialog.dismiss();
                }
            });
        } else {

            this.bAceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    crearTurno();
                    dialog.dismiss();
                }
            });
        }
        this.tvCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        this.bagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_INSERT); //ACTION_EDIT
                //intent.setType("vnd.android.cursor.item/event");
                intent.setDataAndType(CalendarContract.Events.CONTENT_URI,"vnd.android.cursor.item/event");
                intent.putExtra("beginTime", miCalendar.getTimeInMillis());
                intent.putExtra("allDay", false);
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION,"Caseros 795,A4400 Salta,Argentina");
                //intent.putExtra("rrule", "FREQ=YEARLY");
                intent.putExtra("endTime", miCalendar.getTimeInMillis()+60*60*1000);
                intent.putExtra("title", servicioNom);
                startActivity(intent);
                crearTurno();

                dialog.dismiss();
            }
        });
        this.dialog.show();
    }

    public String arreglarfecha(String fechaOriginal){
        String anio,mes,dia,terminado;
        anio = fechaOriginal.substring(0,4);
        mes = fechaOriginal.substring(5,7);
        dia = fechaOriginal.substring(8);
        terminado = dia + "/" + mes + "/" + anio;
        return terminado;
    }

    public void crearTurno(){
        RequestParams param = new RequestParams();
        param.add("type","agregarTurno");
        param.add("usuarioID",idU);
        param.add("fecha",fechBD);
        param.add("hora",horaBD);
        param.add("servicio",servicioBD);
        this.listaTurno.clear();
        clsWebRequest.post(context,"apiSalon.php",param,new FragmentTurnos.ResponseSubir());
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).attach(this).commit();

    }

    public void crearTurnoAdmin(){
        RequestParams param = new RequestParams();
        param.add("type","agregarTurnoEmpleado");
        param.add("nombre",nom);
        param.add("apellido",ape);
        param.add("dni",dni);
        param.add("emai",email);
        param.add("usuarioID",idU);
        param.add("fecha",fechBD);
        param.add("hora",horaBD);
        param.add("servicio",servicioBD);
        this.listaTurno.clear();
        clsWebRequest.post(context,"apiSalon.php",param,new FragmentTurnos.ResponseSubir());
        refresh();
    }

    public void refresh(){
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }

    @Override
    public void onNoteClick(int position) {
        final Turno turno = listaTurno.get(position);
        final Dialog dialog = new Dialog(context);
        TextView tvfecha,tvhora,tvcorte;
        if (idTipoUsuario.equals("1")){//si el usuario es un empleado
            dialog.setContentView(R.layout.activity_turnos_detalles);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
            TextView tvnombreE,tvdniE,tvemailE;
            tvnombreE = (TextView) dialog.findViewById(R.id.nombreUE);
            tvdniE = (TextView) dialog.findViewById(R.id.dniUE);
            tvemailE = (TextView) dialog.findViewById(R.id.emailUE);
            tvfecha = (TextView) dialog.findViewById(R.id.fechaUE);
            tvhora = (TextView) dialog.findViewById(R.id.horaUE);
            tvcorte = (TextView) dialog.findViewById(R.id.serviUE);
            Cliente c = turno.getCliente();
            String namee = c.getNombre() + " " + c.getApellido();
            tvnombreE.setText(namee);
            tvdniE.setText(c.getDni());
            tvemailE.setText(c.getEmail());
            tvfecha.setText(turno.getFecha());
            tvhora.setText(turno.getHora());
            tvcorte.setText(turno.getCorte());
            dialog.show();
        } else {
            dialog.setContentView(R.layout.layout_turno_creado);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
            //dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            tvfecha = dialog.findViewById(R.id.TVFechaTurnoC);
            tvhora = dialog.findViewById(R.id.TVHoraTurnoC);
            tvcorte = dialog.findViewById(R.id.TVServiTurnoC);
            tvfecha.setText(turno.getFecha());
            tvhora.setText(turno.getHora());
            tvcorte.setText(turno.getCorte());
            TextView tvCerrar = (TextView) dialog.findViewById(R.id.XCerrarC);
            tvCerrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            Button cancelar = (Button) dialog.findViewById(R.id.bCancelarTurno);
            if (idTipoUsuario.equals("1")) {
                cancelar.setVisibility(View.GONE);
            }
            cancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //crearTurno();////Eliminar el turno
                    dialogConfirm(turno.getId());
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

    public void dialogConfirm(String idTurno){
        final String idt = idTurno;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("¿Borrar Elemento?");
        builder.setMessage("¿Desea cancelar su turno?");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                RequestParams param = new RequestParams();
                param.add("idTurno",idt);
                param.add("type","borrarTurno");
                listaTurno.clear();
                clsWebRequest.post(context,"apiSalon.php",param, new JsonHttpResponseHandler());
                refresh();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        builder.show();
    }



    private class ResponseHandler extends JsonHttpResponseHandler{

        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            try {
                JSONArray jAray = response.getJSONArray("turnosUsuario");
                for (int i = 0;i<jAray.length();i++){
                    JSONObject jo = jAray.getJSONObject(i);
                    String id,nombre,apellido,fecha,fechaf,hora,descripcion;
                    id = jo.getString("idTurno");
                    nombre = jo.getString("nombre");
                    apellido = jo.getString("apellido");
                    fecha = jo.getString("fecha");
                    fechaf = arreglarfecha(fecha);
                    descripcion = jo.getString("descripcion");
                    hora = jo.getString("hora").substring(0,5);
                    Cliente cliente = new Cliente(jo.getString("idusuario"),nombre,apellido,jo.getString("email"),jo.getString("dni"));
                    Turno turn = new Turno(id,nombre,apellido,fechaf,hora,descripcion,cliente);
                    listaTurno.add(turn);
                }
                if (listaTurno.size() != 0){
                    tvAviso.setVisibility(View.GONE);
                }
                rv = (RecyclerView) view.findViewById(R.id.objRecyclerViewT);
                rv.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(getActivity());
                rv.setLayoutManager(layoutManager);
                adapter = new AdapterTurnosU(activity,listaTurno,frag);
                rv.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            Log.d("IDM2020::4", "Error " + statusCode);
            super.onFailure(statusCode, headers, responseString, throwable);
        }

        @Override
        public void onFinish() {
            super.onFinish();


        }
    }

    private class ResponseSubir extends JsonHttpResponseHandler {
        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            try {
                if (response.getBoolean("error")) {
                    // error de login
                    Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                } else {
                }

            } catch (JSONException e) {

                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            Log.d("IDM2020::4", "Error " + statusCode);
            super.onFailure(statusCode, headers, responseString, throwable);
        }


        @Override
        public void onFinish() {
            super.onFinish();

        }
    }

    private class ResponseHora extends JsonHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            Timepoint[] timepoints;
            try {
                JSONArray jAray = response.getJSONArray("horas");
                timepoints = new Timepoint[jAray.length()];
                for (int i = 0; i < jAray.length(); i++) {
                    JSONObject jo = jAray.getJSONObject(i);
                    String hora;
                    hora = jo.getString("hora").substring(0, 2);
                    timepoints[i] = new Timepoint(Integer.parseInt(hora));

                }
                vistaReloj(timepoints);

            } catch (JSONException e) {
                timepoints = new Timepoint[0];
                vistaReloj(timepoints);
                e.printStackTrace();
            }

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            Log.d("IDM2020::4", "Error " + statusCode);
            super.onFailure(statusCode, headers, responseString, throwable);
        }

        @Override
        public void onFinish() {
            super.onFinish();


        }
    }



}
