## 🧱 Build Instructions

### Requirements

- CMake ≥ 3.14
- Python ≥ 3.6
- pybind11 (`pip install pybind11`)
- Java JDK ≥ 11
- A C++17 compatible compiler

---

### 🔧 Building the C++ Core

```bash
mkdir build && cd build
cmake ..
make
```

### 🐍 Python Usage
Build and Install
```bash
# From the project root
cd build
cmake .. -DPYTHON_BINDINGS=ON
make
```

# Copy the .so/.pyd into the python/ directory or install
```
cd ..
pip install .
```

## ☕ Java Usage
Compile Java and Generate JNI Headers
```bash
cd java
javac -h . com/example/MathUtils.java
```
Build and Run with JNI
```bash
cd ..
mkdir build && cd build
cmake .. -DJAVA_BINDINGS=ON
make
```

# Run the Java program
java -Djava.library.path=. com.example.Main
