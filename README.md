![image](https://user-images.githubusercontent.com/84046974/199115870-66d4a3d5-f512-4012-aa5a-b64899342d70.png)
![1593_mintotal](https://github.com/user-attachments/assets/32d05e74-8b9c-48da-b7f4-9ee9d4f4cf55)

# Avoid Number Overflow
* Original (might cause overflow): `int mid = (start + end) / 2);`
* Improvement 1: `int mid = start + (end - start)/2);`
* Improvement 2: `int mid = (start + end) >>> 1; //">>>" is logical shift, so it does not extend the signed part`
