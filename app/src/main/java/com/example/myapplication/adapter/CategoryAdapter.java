package com.example.myapplication.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.example.myapplication.model.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Category> category;
    private String url="http://192.168.8.103/perpus/api/category/";

    public CategoryAdapter(Context context,ArrayList<Category> category){
        this.context = context;
        this.category = category;
    }

    @NonNull
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        view=layoutInflater.inflate(R.layout.category_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.title.setText(category.get(position).getCategory());
        holder.no.setText("#"+String.valueOf(position+1));
        holder.editCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = category.get(position).getId();
                String value = category.get(position).getCategory();
                editCategory(id,value);
            }
        });
        holder.deleteCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = category.get(position).getId();
                deleteCategory(id);
            }
        });
    }

    private void deleteCategory(final int id) {
        TextView close, judul;
        final EditText cat;
        Button submit;
        final Dialog dialog;

        dialog=new Dialog(context);

        dialog.setContentView(R.layout.activity_modcat);

        close = (TextView) dialog.findViewById(R.id.txtclose);
        judul = (TextView) dialog.findViewById(R.id.judul);

        judul.setText("Delete Kategoriya");
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        submit = (Button) dialog.findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Submit("DELETE","",dialog,id);
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void editCategory(final int id,String value) {
        TextView close, judul;
        final EditText cat;
        Button submit;
        final Dialog dialog;

        dialog=new Dialog(context);

        dialog.setContentView(R.layout.activity_modcat);

        close = (TextView) dialog.findViewById(R.id.txtclose);
        judul = (TextView) dialog.findViewById(R.id.judul);

        judul.setText("Edit Kategoriya");
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        cat = (EditText) dialog.findViewById(R.id.cat);
        submit = (Button) dialog.findViewById(R.id.submit);

        cat.setText(value);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = cat.getText().toString();
                Submit("PUT",data,dialog,id);
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void Submit(String method,final String data,final Dialog dialog,final int id) {
        if (method == "PUT") {
            StringRequest request = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    dialog.dismiss();

                    Toast.makeText(context, "Data Berhaliditannddn", Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Data Gagel", Toast.LENGTH_LONG).show();

                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("cat", data);
                    params.put("id", String.valueOf(id));
                    return params;
                }
            };
            Volley.newRequestQueue(context).add(request);
        } else if (method == "DELETE"){
            StringRequest request = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    dialog.dismiss();

                    Toast.makeText(context, "Data Berhaliditannddn", Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Data Gagel", Toast.LENGTH_LONG).show();

                }
            }) ;
            Volley.newRequestQueue(context).add(request);
        }
    }

    @Override
    public int getItemCount() {
        return category.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView title,no;
        private ImageView editCat,deleteCat;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            no = (TextView) itemView.findViewById(R.id.category);
            title = (TextView) itemView.findViewById(R.id.nameCat);
            editCat =(ImageView) itemView.findViewById(R.id.editCategory);
            deleteCat = (ImageView) itemView.findViewById(R.id.deleteCategory);
        }
    }
}
