# 🤖 KleanBot – Android Chatbot App using OpenAI GPT-3.5 and Clean Architecture

KleanBot is a Kotlin-based Android chatbot app that integrates OpenAI's GPT-3.5 Turbo API (or anyother) and follows modern clean architecture principles for scalable, testable development.

## Android Chatbot Overview – Powered by OpenAI

## 🚀 Features

- MVVM + Clean Architecture (SOLID)
- Modularized project structure for scalability
- OpenAI GPT-3.5 Turbo integration
- Retrofit + Coroutines + Flow for network handling
- State management with sealed classes
- Dependency Injection using Koin
- Splash screen & Home screen (chat UI)
- Clean separation of concerns across layers
- Ready for testing & extension

---

## 📸 Screenshots

<img src="https://github.com/user-attachments/assets/84e8d54c-c541-4685-9850-ed4ce3ed35d9" width="300" height="600" />

---

## 🧠 Tech Stack

| Layer       | Tech                      |
|-------------|---------------------------|
| UI          | XML                       |
| ViewModel   | Kotlin + Coroutines       |
| Domain      | UseCases + Repositories   |
| Data        | Retrofit                  |
| Networking  | Retrofit + OkHttp         |

---

## 🗂️ Project Structure

```
KleanBot/
├── App/                # Application class
├── data/
│   └── dataSources/    # Retrofit, API 
│   └── entities/      
│   └── repository/      
├── di                  # Manual DI
├── domain/        
│   └── UseCases/      
│   └── repository/     # Interfaces
├── presentation/      
│   └── adapters/      
│   └── enums/      
│   └── ui/      
│   └── uiStates/      
│   └── viewModel/   
├── utilities/         
 
```

---

## 🔐 OpenAI Integration

Make sure to add your API key in `utilities > ConstantUtils.kt`:

```properties
OPEN_AI_API_KEY="your_api_key_here"
```

And then access it in your build config or DI setup safely.

---

## 🛠️ Setup Instructions

1. Clone the repo

```
git clone https://github.com/yourusername/KleanBot.git
```

2. Add your OpenAI API key in `utilities > ConstantUtils.kt`.

3. Sync project & run.

---

## 🔑 Key Features of KleanBot

- 🤖 AI-Powered Chat using GPT-3.5 Turbo
- 💬 Kotlin-based clean architecture for Android apps
- 🔁 Seamless integration with OpenAI API
- ⚡ Asynchronous chat with Kotlin Coroutines and Flow
- 🧪 Testable architecture using Koin DI and MVVM

## 💡 Inspiration

Built to help devs understand:
- How to integrate AI into native apps
- How to structure clean Android apps
- How to impress their tech leads 😎

---

## 🧙‍♂️ Cool Tip of the Day

> _“Don't let your architecture rot. Klean it up.”_

---

## 📈 Why Use KleanBot?

Whether you're building a personal AI assistant, learning how to use GPT in Android apps, or exploring clean architecture with Kotlin, KleanBot offers a production-ready base to accelerate your development.

## 📜 License

MIT © 2025 — [Sohaib Ahmed](https://github.com/epegasus)

---

## 🙌 Contributions

PRs, Issues, and Stars are welcome.  
If you make it better, the world becomes 0.001% smarter.
