package com.example.studybuddy.services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.studybuddy.model.Lesson;
import com.example.studybuddy.model.Teacher;
import com.example.studybuddy.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DatabaseService {
    /// tag for logging
    /// @see Log
    private static final String TAG = "DatabaseService";

    /// callback interface for database operations
    /// @param <T> the type of the object to return
    /// @see DatabaseCallback#onCompleted(Object)
    /// @see DatabaseCallback#onFailed(Exception)
    public interface DatabaseCallback<T> {
        /// called when the operation is completed successfully
        void onCompleted(T object);

        /// called when the operation fails with an exception
        void onFailed(Exception e);
    }

    /// the instance of this class
    /// @see #getInstance()
    private static DatabaseService instance;

    /// the reference to the database
    /// @see DatabaseReference
    /// @see FirebaseDatabase#getReference()
    private final DatabaseReference databaseReference;

    /// use getInstance() to get an instance of this class
    /// @see DatabaseService#getInstance()
    private DatabaseService() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    /// get an instance of this class
    /// @return an instance of this class
    /// @see DatabaseService
    public static DatabaseService getInstance() {
        if (instance == null) {
            instance = new DatabaseService();
        }
        return instance;
    }


    // private generic methods to write and read data from the database

    /// write data to the database at a specific path
    /// @param path the path to write the data to
    /// @param data the data to write (can be any object, but must be serializable, i.e. must have a default constructor and all fields must have getters and setters)
    /// @param callback the callback to call when the operation is completed
    /// @return void
    /// @see DatabaseCallback
    private void writeData(@NotNull final String path, @NotNull final Object data, final @Nullable DatabaseCallback<Void> callback) {
        databaseReference.child(path).setValue(data).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (callback == null) return;
                callback.onCompleted(task.getResult());
            } else {
                if (callback == null) return;
                callback.onFailed(task.getException());
            }
        });
    }

    /// read data from the database at a specific path
    /// @param path the path to read the data from
    /// @return a DatabaseReference object to read the data from
    /// @see DatabaseReference

    private DatabaseReference readData(@NotNull final String path) {
        return databaseReference.child(path);
    }


    /// get data from the database at a specific path
    /// @param path the path to get the data from
    /// @param clazz the class of the object to return
    /// @param callback the callback to call when the operation is completed
    /// @return void
    /// @see DatabaseCallback
    /// @see Class
    private <T> void getData(@NotNull final String path, @NotNull final Class<T> clazz, @NotNull final DatabaseCallback<T> callback) {
        readData(path).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e(TAG, "Error getting data", task.getException());
                callback.onFailed(task.getException());
                return;
            }
            T data = task.getResult().getValue(clazz);
            callback.onCompleted(data);
        });
    }

    /// generate a new id for a new object in the database
    /// @param path the path to generate the id for
    /// @return a new id for the object
    /// @see String
    /// @see DatabaseReference#push()

    private String generateNewId(@NotNull final String path) {
        return databaseReference.child(path).push().getKey();
    }

    /// get a user from the database
    /// @param uid the id of the user to get
    /// @param callback the callback to call when the operation is completed
    ///               the callback will receive the user object
    ///             if the operation fails, the callback will receive an exception
    /// @return void
    /// @see DatabaseCallback
    /// @see User
    public void getUser(@NotNull final String uid, @NotNull final DatabaseCallback<User> callback) {
        getData("Users/" + uid, User.class, callback);
    }

    // public methods to interact with the database

    /// create a new user in the database
    /// @param user the user object to create
    /// @param callback the callback to call when the operation is completed
    ///              the callback will receive void
    ///            if the operation fails, the callback will receive an exception
    /// @return void
    /// @see DatabaseCallback
    /// @see User
    public void createNewUser(@NotNull final User user, @Nullable final DatabaseCallback<Void> callback) {
        writeData("Users/" + user.getId(), user, callback);
    }

    /// create a new teacher in the database
    /// @param teacher the teacher object to create
    /// @param callback the callback to call when the operation is completed
    ///              the callback will receive void
    ///             if the operation fails, the callback will receive an exception
    /// @return void
    /// @see DatabaseCallback
    /// @see Teacher
    public void createNewTeacher(@NotNull final Teacher teacher, @Nullable final DatabaseCallback<Void> callback) {
        writeData("teachers/" + teacher.getId(), teacher, callback);
    }


    /// update  teacher in the database
    /// @param teacher the teacher object to update
    /// @param callback the callback to call when the operation is completed
    ///              the callback will receive void
    ///             if the operation fails, the callback will receive an exception
    /// @return void
    /// @see DatabaseCallback
    /// @see Teacher

    public void UpdateTeacher(@NotNull final Teacher teacher, @Nullable final DatabaseCallback<Void> callback) {
        writeData("teachers/" + teacher.getId(), teacher, callback);
    }




    /// create a new lesson in the database
    /// @param lesson the lesson object to create
    /// @param callback the callback to call when the operation is completed
    ///               the callback will receive void
    ///              if the operation fails, the callback will receive an exception
    /// @return void
    /// @see DatabaseCallback
    /// @see Lesson
    public void createNewLesson(@NotNull final Lesson lesson, @Nullable final DatabaseCallback<Void> callback) {
        writeData(  "teachers/"+lesson.getTeacher().getId()+"/myLessons/" + lesson.getId(), lesson, callback);
        writeData(  "Users/" + lesson.getStudent().getId()+"/myLessons/" + lesson.getId(), lesson, callback);


    }




    /// get a teacher from the database
    /// @param teacherId the id of the teacher to get
    /// @param callback the callback to call when the operation is completed
    ///               the callback will receive the teacher object
    ///              if the operation fails, the callback will receive an exception
    /// @return void
    /// @see DatabaseCallback
    /// @see Teacher
    public void getTeacher(@NotNull final String teacherId, @NotNull final DatabaseCallback<Teacher> callback) {
        getData("teachers/" + teacherId, Teacher.class, callback);
    }

    /// get a lesson from the database
    /// @param lessonId the id of the lesson to get
    /// @param callback the callback to call when the operation is completed
    ///                the callback will receive the lesson object
    ///               if the operation fails, the callback will receive an exception
    /// @return void
    /// @see DatabaseCallback
    /// @see Lesson
    public void getLesson(@NotNull final String lessonId, @NotNull final DatabaseCallback<Lesson> callback) {
        getData("lessons/" + lessonId, Lesson.class, callback);
    }


    /// generate a new id for a new teacher in the database
    /// @return a new id for the teacher
    /// @see #generateNewId(String)
    /// @see Teacher
    public String generateTeacherId() {
        return generateNewId("teachers");
    }

    /// generate a new id for a new lesson in the database
    /// @return a new id for the lesson
    /// @see #generateNewId(String)
    /// @see Lesson
    public String generateLessonId() {
        return generateNewId("lessons");
    }

    /// get all the teachers from the database
    /// @param callback the callback to call when the operation is completed
    ///              the callback will receive a list of teacher objects
    ///            if the operation fails, the callback will receive an exception
    /// @return void
    /// @see DatabaseCallback
    /// @see List
    /// @see Teacher
    /// @see #getData(String, Class, DatabaseCallback)


    public void getTeachers(@NotNull final DatabaseCallback<List<Teacher>> callback) {
        readData("teachers").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e(TAG, "Error getting data", task.getException());
                callback.onFailed(task.getException());
                return;
            }
            List<Teacher> teachers = new ArrayList<>();
            task.getResult().getChildren().forEach(dataSnapshot -> {
                Teacher teacher = dataSnapshot.getValue(Teacher.class);

                teacher=new Teacher(teacher);
                Log.d(TAG, "Got teacher: " + teacher);
                teachers.add(teacher);
            });

            callback.onCompleted(teachers);
        });
    }



    /// get all the users from the database
    /// @param callback the callback to call when the operation is completed
    ///              the callback will receive a list of teacher objects
    ///            if the operation fails, the callback will receive an exception
    /// @return void
    /// @see DatabaseCallback
    /// @see List
    /// @see Teacher
    /// @see #getData(String, Class, DatabaseCallback)
    public void getUsers(@NotNull final DatabaseCallback<List<User>> callback) {
        readData("Users").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e(TAG, "Error getting data", task.getException());
                callback.onFailed(task.getException());
                return;
            }
            List<User> users = new ArrayList<>();
            task.getResult().getChildren().forEach(dataSnapshot -> {
                User user = dataSnapshot.getValue(User.class);
                Log.d(TAG, "Got user: " + user);
                users.add(user);
            });

            callback.onCompleted(users);
        });
    }


    public void submitAddLessonForTeacher(Lesson lesson, @Nullable final DatabaseCallback<Void> callback) {

        writeData("TeacherLessonSchedule/"+lesson.getTeacher().getId()+"/"+ lesson.getDate().substring(6)+"/" +lesson.getDate().substring(3,5)+"/"+lesson.getDate().substring(0,2)+"/"+ lesson.getId(), lesson, callback);

        // writeData("coaches/"+workout.getCoachId()+  "/workouts/" + workout.getId(), workout, callback);
    }



    // New public method to submit a workout request
    public void submitAddLessonForStudent(Lesson lesson, @Nullable final DatabaseCallback<Void> callback) {

        writeData("StudentLessonSchedule/"+lesson.getStudent().getId()+"/"+ lesson.getDate().substring(6)+"/"+lesson.getDate().substring(3,5)+"/" +lesson.getDate().substring(0,2)+"/"+ lesson.getId(), lesson, callback);
        //  writeData("trainees/"+workout.getTraineeId()+  "/workouts/" + workout.getId(), workout, callback);
    }


    public void getTecherLessons( String uid,@NotNull final DatabaseCallback<List<Lesson>> callback) {
        readData("TeacherLessonSchedule/"+uid+"/myLessons/" ).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e(TAG, "Error getting data", task.getException());
                callback.onFailed(task.getException());
                return;
            }
            List<Lesson> lessonList = new ArrayList<>();
            task.getResult().getChildren().forEach(dataSnapshot -> {
                Lesson lesson = dataSnapshot.getValue(Lesson.class);
                Log.d(TAG, "Got lesson: " + lesson);
                lessonList.add(lesson);
            });

            Collections.sort(lessonList, new Comparator<Lesson>() {
                @Override
                public int compare(Lesson lesson1, Lesson lesson2) {
                    return lesson1.getDate().compareTo(lesson2.getDate()); // Compare based on date
                }
            });

            callback.onCompleted(lessonList);
        });
    }
    public void updateUser(User currentUser, DatabaseCallback<Void> databaseCallback) {
        createNewUser(currentUser, databaseCallback);
    }




    public void getLessonForTeacher( String uid,   @NotNull final DatabaseCallback<List<Lesson>> callback) {

        String path="TeacherLessonSchedule/" + uid;
        DatabaseReference myRef=  readData(path);





        List<Lesson> lessons = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Log.d("postSnapshot", "num  is: " + postSnapshot.getKey());

                    for (DataSnapshot dm : postSnapshot.getChildren()) {

                        Log.d("dm", "num  is: " + dm.getKey());
                        for (DataSnapshot dd : dm.getChildren()) {


                            Log.d("dd", "num  is: " + dd.getKey());
                            for (DataSnapshot value : dd.getChildren()) {
                                Lesson lesson = value.getValue(Lesson.class);


                                lessons.add(lesson);
                                Log.d("workout", "Value is: " + lesson);
                            }


                        }
                    }

                }


                callback.onCompleted(lessons);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });


    }







    public void getLessonForStudent( String uid,@NotNull final DatabaseCallback<List<Lesson>> callback) {

        String path = "StudentLessonSchedule/" + uid;
        DatabaseReference myRef = readData(path);


        List<Lesson> lessons = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                LocalDate currentDate = LocalDate.now();


                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Log.d("postSnapshot", "num  is: " + postSnapshot.getKey());

                    for (DataSnapshot dm : postSnapshot.getChildren()) {

                        Log.d("dm", "num  is: " + dm.getKey());
                        for (DataSnapshot dd : dm.getChildren()) {


                            Log.d("dd", "num  is: " + dd.getKey());
                            for (DataSnapshot value : dd.getChildren()) {

                                Lesson lesson = value.getValue(Lesson.class);
                                String stDate = lesson.getDate().substring(6) + "-" + lesson.getDate().substring(3, 5) + "-" + lesson.getDate().substring(0, 2);

                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                LocalDate date1 = LocalDate.parse(stDate, formatter);

                                LocalDate date2 = LocalDate.parse(currentDate.toString(), formatter);


                                if (date1.isAfter(date2) || date1.equals(date2)) {

                                    lessons.add(lesson);
                                }
                                Log.d("workout", "Value is: " + lesson);
                            }


                        }
                    }

                }


                callback.onCompleted(lessons);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }

        public void updateLessonStatus(@NotNull final Lesson lesson, boolean status, @Nullable final DatabaseCallback<Void> callback) {
            lesson.setStatus(status);

            // Update for teacher
            writeData("TeacherLessonSchedule/" + lesson.getTeacher().getId() + "/" + lesson.getDate().substring(6) + "/" +
                    lesson.getDate().substring(3,5) + "/" + lesson.getDate().substring(0,2) + "/" + lesson.getId(), lesson, callback);

            // Update for student
            writeData("StudentLessonSchedule/" + lesson.getStudent().getId() + "/" + lesson.getDate().substring(6) + "/" +
                    lesson.getDate().substring(3,5) + "/" + lesson.getDate().substring(0,2) + "/" + lesson.getId(), lesson, callback);

            // Optional: update in user profiles too, if needed
            writeData("Users/" + lesson.getStudent().getId() + "/myLessons/" + lesson.getId(), lesson, null);
            writeData("teachers/" + lesson.getTeacher().getId() + "/myLessons/" + lesson.getId(), lesson, null);
        }

    }



