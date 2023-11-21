package com.Padhantueducation.view_section.ui.doubts;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.Padhantueducation.CommonClasses.FileUtils;
import com.Padhantueducation.CommonClasses.Utils;
import com.Padhantueducation.R;
import com.Padhantueducation.Session.Session;
import com.Padhantueducation.models.SubmitQueryResult;
import com.Padhantueducation.remote.APIService;
import com.Padhantueducation.remote.ApiClient;
import com.Padhantueducation.view_section.ui.home.Models.ClassResult;
import com.Padhantueducation.view_section.ui.home.Models.GetClass;
import com.Padhantueducation.view_section.ui.home.Models.SubjectModel;
import com.Padhantueducation.view_section.ui.home.Models.SubjectResult;
import com.Padhantueducation.view_section.ui.home.adapter.ClassAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscussionForumFragment extends Fragment {
    TextView pdf_name;
    View view;
    Spinner class_spinner, subject_spinner;
    EditText et_query;
    Button Submit;
    private ArrayAdapter<GetClass> countryArrayAdapter;
    ClassAdapter classAdapter;
    private ArrayList<GetClass> countries;
    ArrayList<String> spin_class = new ArrayList<>();
    ArrayList<String> spin_subject = new ArrayList<>();
    public HashMap<Integer, GetClass> ClassHashMap = new HashMap<Integer, GetClass>();
    public HashMap<Integer, SubjectModel> SubjectHashMap = new HashMap<Integer, SubjectModel>();
    LinearLayout line_pdf_upload;
    int current_country_code;
    Session session;
    String ClassId, Subject_id;
    private int PICK_PDF_REQUEST = 1;
    File filepdf;
    private static final int STORAGE_PERMISSION_CODE = 123;
ImageView iv_back;

    public DiscussionForumFragment() {

    }

    Uri UploadPdfUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_discussion_forum, container, false);

        initView();


        get_Class();
        requestStoragePermission();

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_query.getText().toString().equals("")) {
                    et_query.setError("Please Enter Your Query");
                } else {
                    if (filepdf != null) {
                        Uri myUri = Uri.parse(String.valueOf(UploadPdfUri));
                        String pdfUrl = FileUtils.getPath(getActivity(), myUri);
                        File file = new File(pdfUrl);

                        Log.e("check file path ", file.toString());
                        get_Quesry_Submit(file, et_query.getText().toString(), session.getUserId(), ClassId, Subject_id, Utils.Get_Device_ID(getActivity()));

                    } else {
                        get_Quesry_Submit2(et_query.getText().toString(), session.getUserId(), ClassId, Subject_id, Utils.Get_Device_ID(getActivity()));
                    }
                }
            }
        });











        line_pdf_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });


        class_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < ClassHashMap.size(); i++) {
                    if (ClassHashMap.get(i).getClass_name().equals(class_spinner.getItemAtPosition(position))) {
                        ClassId = ClassHashMap.get(i).getId();
                        //  Toast.makeText(getActivity(), ""+CountryId, Toast.LENGTH_SHORT).show();
                    }
                }
                getSubjectList(ClassId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


        subject_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                for (int i = 0; i < SubjectHashMap.size(); i++) {
                    if (SubjectHashMap.get(i).getSubject_name().equals(subject_spinner.getItemAtPosition(position))) {
                        Subject_id = SubjectHashMap.get(i).getId();
                    }
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


        return view;
    }

    private void get_Quesry_Submit(File pdfUrl, String user_query, String user_id, String class_id, String subject_id, String device_id) {
        Utils.showProgressDialog(getActivity());
        APIService apiInterface = ApiClient.getClient().create(APIService.class);

        RequestBody muser_id = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody mclass_id = RequestBody.create(MediaType.parse("text/plain"), class_id);
        RequestBody msubject_id = RequestBody.create(MediaType.parse("text/plain"), subject_id);
        RequestBody muser_query = RequestBody.create(MediaType.parse("text/plain"), user_query);
        RequestBody mdevice_id = RequestBody.create(MediaType.parse("text/plain"), device_id);


        RequestBody requestBody = RequestBody.create(MediaType.parse("application/pdf*"), pdfUrl);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", pdfUrl.getName(), requestBody);


        Call<SubmitQueryResult> call = apiInterface.UploadQuery(muser_id, mclass_id, msubject_id, muser_query, mdevice_id, fileToUpload);
        call.enqueue(new Callback<SubmitQueryResult>() {
            @Override
            public void onResponse(Call<SubmitQueryResult> call, retrofit2.Response<SubmitQueryResult> response) {
                Utils.dismissProgressDialog();
                if (response.body().getResult().equals("true")) {
                    et_query.setText("");
                    pdf_name.setText("Attachment");
                    Toast.makeText(getActivity(), "Your Query Submitted Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    if (response.body().getMsg().equals("invalid_token")) {
                        Utils.Logout_Api(session.getUserId(), getActivity());
                    }
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SubmitQueryResult> call, Throwable t) {
                Utils.dismissProgressDialog();
                Log.e("ccheckResponce", " :" + t.getMessage());
                //Toast.makeText(DeliverydetailsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void get_Quesry_Submit2(String user_query, String user_id, String class_id, String subject_id, String device_id) {
        Utils.showProgressDialog(getActivity());
        APIService apiInterface = ApiClient.getClient().create(APIService.class);

        RequestBody muser_id = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody mclass_id = RequestBody.create(MediaType.parse("text/plain"), class_id);
        RequestBody msubject_id = RequestBody.create(MediaType.parse("text/plain"), subject_id);
        RequestBody muser_query = RequestBody.create(MediaType.parse("text/plain"), user_query);
        RequestBody mdevice_id = RequestBody.create(MediaType.parse("text/plain"), device_id);

        //RequestBody requestBody = RequestBody.create(MediaType.parse("application/pdf*"), filepdf);
        //  MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", filepdf.getName(), requestBody);


        Call<SubmitQueryResult> call = apiInterface.UploadQuery2(muser_id, mclass_id, msubject_id, muser_query, mdevice_id);
        call.enqueue(new Callback<SubmitQueryResult>() {
            @Override
            public void onResponse(Call<SubmitQueryResult> call, retrofit2.Response<SubmitQueryResult> response) {
                Utils.dismissProgressDialog();
                if (response.body().getResult().equalsIgnoreCase("true")) {
                    et_query.setText("");
                    pdf_name.setText("Attachment");
                    Toast.makeText(getActivity(), "Your Query Submitted Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    if (response.body().getMsg().equals("invalid_token")) {
                        Utils.Logout_Api(session.getUserId(), getActivity());
                    }
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SubmitQueryResult> call, Throwable t) {
                Utils.dismissProgressDialog();
                Log.e("ccheckResponce", " :" + t.getMessage());
                //Toast.makeText(DeliverydetailsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initView() {
        session = new Session(getActivity());
        line_pdf_upload = view.findViewById(R.id.line_pdf_upload);
        class_spinner = view.findViewById(R.id.class_spinner);
        subject_spinner = view.findViewById(R.id.subject_spinner);
        et_query = view.findViewById(R.id.et_email);
        pdf_name = view.findViewById(R.id.pdf_name);
        Submit = view.findViewById(R.id.Submit);

    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(getActivity(), "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(getActivity(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);


        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && resultData != null && resultData.getData() != null) {
            UploadPdfUri = resultData.getData();
            String Fpath = FileUtils.getPath(getActivity(), UploadPdfUri);
            filepdf = new File(Fpath);
            String filename = filepdf.getName();
            pdf_name.setText(filename);
            //Utils.Toast(getActivity(), "Your File Has Been Uploaded");
        }
    }

    private void getSubjectList(String cource_id) {
        spin_subject.clear();
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<SubjectResult> userCall = service.Get_Subjects(session.getUserId(), Utils.Get_Device_ID(getActivity()), cource_id);
        userCall.enqueue(new Callback<SubjectResult>() {
            @Override
            public void onResponse(Call<SubjectResult> call, Response<SubjectResult> response) {
                try {
                    if (response != null) {
                        Log.e("TAG", "subject id ddd: " + new Gson().toJson(response.body()));
                        Log.e("subject id", "" + response.body().getData());
                        if (response.body().getResult().equals("true")) {
                            // Toast.makeText(context, "Successfull", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                spin_subject.add(i, response.body().getData().get(i).getSubject_name());
                                SubjectHashMap.put(i, response.body().getData().get(i));
                                Log.e("SubjectHashMap", "" + SubjectHashMap);
                            }
                            Log.e("spin subject", "" + spin_subject.size());
                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spin_subject); //selected item will look like a spinner set from XML
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            subject_spinner.setAdapter(spinnerArrayAdapter);
                        } else {
                            Toast.makeText(getActivity(), "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    Log.e("error_i_report", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<SubjectResult> call, Throwable t) {
                Log.e("error_i_report1", t.getMessage());
            }
        });

    }


    private void get_Class() {
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ClassResult> userCall = service.Get_ALL_CLASSES(session.getUserId(), Utils.Get_Device_ID(getActivity()));
        userCall.enqueue(new Callback<ClassResult>() {
            @Override
            public void onResponse(Call<ClassResult> call, Response<ClassResult> response) {
                try {
                    if (response != null) {
                        Log.e("res_i_report", "" + response.body().getData());
                        if (response.body().getResult().equals("true")) {
                            // Toast.makeText(context, "Successfull", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                spin_class.add(i, response.body().getData().get(i).getClass_name());
                                ClassHashMap.put(i, response.body().getData().get(i));
                                Log.e("ClassHashMap", "" + ClassHashMap);
                            }

                            Log.e("spin_countsize", "" + spin_class.size());
                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spin_class); //selected item will look like a spinner set from XML
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            class_spinner.setAdapter(spinnerArrayAdapter);

                        } else {
                            Toast.makeText(getActivity(), "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }

                    }
                } catch (Exception e) {
                    Log.e("error_i_report", e.getMessage());
                }
                //  progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ClassResult> call, Throwable t) {
                // progressDialog.dismiss();
                Log.e("error_i_report1", t.getMessage());
                //Toast.makeText(AllCountries.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }
}
