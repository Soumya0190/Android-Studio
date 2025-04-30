# Android-Studio
https://canvas.eee.uci.edu/courses/21534/assignments/401475

---
DEMO VIDEO: https://youtu.be/RO4jmFCfwNs

---
Sure! Here's how this Firebase-based Reddit-like Android app can be explained using the **STAR method** (Situation, Task, Action, Result):

---

### ✅ **S - Situation**
In a mobile application development course, I was tasked with building an Android app that demonstrates the ability to work with cloud databases and implement core Android development skills, including GUI design, Java classes, and real-time data operations.

---

### ✅ **T - Task**
The goal was to develop a simplified Reddit-like app for Android. The app needed to:
- Allow users to post top-level messages and replies.
- Maintain a hierarchy of parent and child posts (only 2 levels).
- Enable upvoting and deleting messages.
- Store and sync all data in real time using **Google Firebase**.

---

### ✅ **A - Action**
To accomplish this:
- I designed a **Firebase data schema** where each post had fields like ID, content, parent ID, score, and timestamp.
- I created Java classes for messages (`Message.java`) to encapsulate post data.
- Used **Firebase Realtime Database** to:
  - Add, retrieve, and delete posts.
  - Automatically sync UI updates when data changes.
- Built a **RecyclerView** for displaying parent posts in sorted order by score.
- Created an activity (`ReplyActivity`) for replying to any post and linking replies to the parent.
- Integrated **click listeners** to upvote posts and used Firebase transactions to increment scores.
- Implemented **recursive deletion** so when a parent post is removed, its replies are also deleted.
- Designed clean XML layouts with Material UI components for a user-friendly interface.

---

### ✅ **R - Result**
The resulting app:
- Successfully demonstrated Firebase integration with full **CRUD** functionality.
- Provided real-time updates across users/devices.
- Allowed users to interact with posts through voting and replying.
- Met all the required functionality criteria for the project.
- Received positive feedback from the instructor for clean code, clear UI, and thoughtful hierarchy handling.

---

Would you like help writing a resume bullet point for this experience or showcasing it in a portfolio?

---

Thanks for sharing the project details. Here's a breakdown of how to **run your Lab 5 Android app GUI**, particularly if you're doing the **Reddit-style individual app** or the **group Firebase-connected custom app**.

---

## ✅ How to Run the Project GUI (Android App)

### 🔧 1. **Set Up Android Studio**
- Open **Android Studio**.
- Select **"Open an existing project"** and choose your Lab5 project folder.
- Wait for **Gradle to sync** and dependencies to resolve.

---

### 🌐 2. **Firebase Setup**
Ensure Firebase is connected:
1. Go to [Firebase Console](https://console.firebase.google.com/).
2. Create a Firebase project.
3. Add your Android app using its **package name** (e.g., `com.example.redditclone`).
4. Download `google-services.json` and place it in:
   ```
   app/ directory
   ```
5. In `build.gradle` (Project):
   ```groovy
   classpath 'com.google.gms:google-services:4.3.15' // or latest
   ```

6. In `build.gradle` (App):
   ```groovy
   apply plugin: 'com.google.gms.google-services'
   dependencies {
       implementation 'com.google.firebase:firebase-database:20.3.0'
   }
   ```

7. Sync project.

---

### ▶️ 3. **Run the App**
- Use an **emulator** or connect a **physical Android device**.
- Click **Run** ▶ in Android Studio.
- Choose the device, then deploy.

---

### 📱 Sample App Behavior (for Reddit-style app)

1. **Home Screen**:
   - Shows a list of **parent posts** (RecyclerView or ListView).
   - Each post has:
     - A score (upvotable).
     - Delete button (to remove post and replies).
     - A "Reply" button (opens reply screen or dialog).

2. **Posting a Message**:
   - Type in a message.
   - Click “Post”.
   - Post appears in the main thread and is saved in Firebase.

3. **Replying**:
   - Tap “Reply” on a parent post.
   - Replies are **indented** and stored as children in Firebase.

4. **Voting**:
   - Tap the message to **upvote** (score increases).
   - Sorts parent posts by score.

5. **Deleting**:
   - Tap delete (or swipe) to remove a post and its replies.

---

### 📦 APK + Submission Files
1. Build APK:
   - In Android Studio: `Build > Build Bundle(s) / APK(s) > Build APK(s)`
   - Find it at: `app/build/outputs/apk/debug/app-debug.apk`

2. Submission Folder:
   ```
   IDNUMBER_UCINETID_Lab5/
   ├── app-debug.apk
   ├── java/
   │   ├── Message.java
   │   ├── MainActivity.java
   │   ├── ReplyActivity.java
   ├── res/layout/
   │   ├── activity_main.xml
   │   ├── activity_reply.xml
   │   └── item_message.xml
   ├── walkthrough.mp4 (2-min demo)
   ```

3. Zip and upload it to Canvas.

---

Would you like a **sample implementation** of the Reddit-style app with Firebase (Java code + XML layout)?

```java
// Firebase Message Model
public class Message {
    public String id;
    public String text;
    public int score;
    public String parentId; // null for parent, id of parent for replies

    public Message() { }

    public Message(String id, String text, int score, String parentId) {
        this.id = id;
        this.text = text;
        this.score = score;
        this.parentId = parentId;
    }
}

// MainActivity.java
public class MainActivity extends AppCompatActivity {
    private EditText inputText;
    private Button postBtn;
    private RecyclerView recyclerView;
    private DatabaseReference dbRef;
    private List<Message> messages = new ArrayList<>();
    private MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText = findViewById(R.id.inputText);
        postBtn = findViewById(R.id.postBtn);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MessageAdapter(messages, this);
        recyclerView.setAdapter(adapter);

        dbRef = FirebaseDatabase.getInstance().getReference("messages");

        postBtn.setOnClickListener(v -> {
            String text = inputText.getText().toString().trim();
            if (!text.isEmpty()) {
                String id = dbRef.push().getKey();
                Message msg = new Message(id, text, 0, null);
                dbRef.child(id).setValue(msg);
                inputText.setText("");
            }
        });

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Message msg = snap.getValue(Message.class);
                    if (msg.parentId == null)
                        messages.add(msg);
                }
                messages.sort((a, b) -> b.score - a.score);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
}

// MessageAdapter.java
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private List<Message> messages;
    private Context context;

    public MessageAdapter(List<Message> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message msg = messages.get(position);
        holder.messageText.setText(msg.text);
        holder.score.setText("Score: " + msg.score);

        holder.itemView.setOnClickListener(v -> {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("messages").child(msg.id);
            ref.child("score").setValue(msg.score + 1);
        });

        holder.deleteBtn.setOnClickListener(v -> {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("messages").child(msg.id);
            ref.removeValue(); // You can recursively delete replies here too
        });

        holder.replyBtn.setOnClickListener(v -> {
            Intent i = new Intent(context, ReplyActivity.class);
            i.putExtra("parentId", msg.id);
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView messageText, score;
        Button replyBtn, deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
            score = itemView.findViewById(R.id.score);
            replyBtn = itemView.findViewById(R.id.replyBtn);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }
}

// ReplyActivity.java
public class ReplyActivity extends AppCompatActivity {
    private EditText replyInput;
    private Button sendReply;
    private String parentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        replyInput = findViewById(R.id.replyInput);
        sendReply = findViewById(R.id.sendReply);
        parentId = getIntent().getStringExtra("parentId");

        sendReply.setOnClickListener(v -> {
            String text = replyInput.getText().toString().trim();
            if (!text.isEmpty()) {
                DatabaseReference db = FirebaseDatabase.getInstance().getReference("messages");
                String id = db.push().getKey();
                Message reply = new Message(id, text, 0, parentId);
                db.child(id).setValue(reply);
                finish();
            }
        });
    }
}
```