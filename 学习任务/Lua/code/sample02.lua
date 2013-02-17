
print(type("hello world")); --> string

print(type("10.4 * 3"));  --> number

print(type(print)); --> function

print(type(type));  --> function

print(type(true));  --> boolean

print(type(nil));  --> nil

print(type(type(x)))  --> string



print(type(a));
a = 10;
print(type(a));
a = "a string";
print(type(a));
a = 10;
print(type(a));
a = print;
a(type(a));
