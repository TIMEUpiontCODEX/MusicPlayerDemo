import requests
import json
import os
import random
import string

# API 基础配置
BASE_URL = "http://124.70.183.156:8000/catalog/api/"  # 更新为正确的API地址
JWT_TOKEN = None  # JWT令牌存储

# 请求头配置
headers = {}  # 登录后会更新认证头

def update_auth_token(token):
    """更新JWT Token"""
    global JWT_TOKEN, headers
    JWT_TOKEN = token
    headers = {"Authorization": f"Bearer {token}"} if token else {}

def generate_random_username(prefix="testuser"):
    """生成随机用户名"""
    random_suffix = ''.join(random.choices(string.digits, k=6))
    return f"{prefix}_{random_suffix}"

def register_user(username, password, email=None):
    """注册新用户"""
    endpoint = "register/"
    data = {
        'username': username,
        'password': password,
        'email': email
    }
    
    try:
        response = requests.post(f"{BASE_URL}{endpoint}", json=data)
        print(f"注册请求URL: {BASE_URL}{endpoint}")
        print(f"注册响应状态码: {response.status_code}")
        print(f"注册响应内容: {response.text}")
        if response.status_code in [200, 201]:
            print(f"用户 {username} 注册成功")
            return True
        else:
            print(f"注册失败: {response.text}")
            return False
    except Exception as e:
        print(f"注册过程中出错: {str(e)}")
        return False

def login_user(username, password):
    """用户登录并获取JWT token"""
    endpoint = "token/"  # 使用JWT的token端点
    data = {
        'username': username,
        'password': password
    }
    
    try:
        response = requests.post(f"{BASE_URL}{endpoint}", json=data)
        print(f"登录请求URL: {BASE_URL}{endpoint}")
        print(f"登录响应状态码: {response.status_code}")
        print(f"登录响应内容: {response.text}")
        if response.status_code == 200:
            token_data = response.json()
            access_token = token_data.get('access')
            if access_token:
                update_auth_token(access_token)
                print(f"用户 {username} 登录成功")
                return True
        print(f"登录失败: {response.text}")
        return False
    except Exception as e:
        print(f"登录过程中出错: {str(e)}")
        return False

def logout_user():
    """用户登出"""
    global JWT_TOKEN, headers
    JWT_TOKEN = None
    headers = {}
    print("用户已登出")
    return True

def upload_song(file_path):
    """上传歌曲文件"""
    endpoint = "songs/upload/"
    
    if not os.path.exists(file_path):
        print(f"文件不存在: {file_path}")
        return None

    try:
        with open(file_path, 'rb') as f:
            files = {
                'file': (os.path.basename(file_path), f, 'audio/mpeg')
            }
            response = requests.post(
                f"{BASE_URL}{endpoint}",
                headers=headers,
                files=files
            )
            
            if response.status_code in [200, 201]:
                print("歌曲上传成功")
                return response.json()
            else:
                print(f"上传失败: {response.text}")
                return None
    except Exception as e:
        print(f"上传过程中出错: {str(e)}")
        return None

def download_song(song_id, save_path=None):
    """下载歌曲"""
    endpoint = f"songs/{song_id}/download/"
    
    try:
        response = requests.get(
            f"{BASE_URL}{endpoint}",
            headers=headers,
            stream=True
        )
        
        if response.status_code != 200:
            print(f"下载失败: {response.text}")
            return None
            
        if not save_path:
            save_path = os.getcwd()
        
        os.makedirs(save_path, exist_ok=True)
        file_path = os.path.join(save_path, f"song_{song_id}.mp3")
        
        with open(file_path, 'wb') as f:
            for chunk in response.iter_content(chunk_size=8192):
                if chunk:
                    f.write(chunk)
                    
        print(f"文件已成功下载到: {file_path}")
        return file_path
        
    except Exception as e:
        print(f"下载过程中出错: {str(e)}")
        return None

def test_unauthorized_operations():
    """测试未登录状态下的操作"""
    print("\n=== 测试未授权操作 ===")
    
    # 确保没有登录状态
    logout_user()
    
    # 测试未登录上传
    print("\n1. 测试未登录上传歌曲")
    test_file = "D:\CloudMusic\Mameyudoufu - Somewhere Else.mp3"
    result = upload_song(test_file)
    assert result is None, "未登录状态不应该能上传歌曲"
    print("✓ 未登录上传测试通过")
    
    # 测试未登录下载
    print("\n2. 测试未登录下载歌曲")
    result = download_song(1, "downloads")
    assert result is None, "未登录状态不应该能下载歌曲"
    print("✓ 未登录下载测试通过")

def test_authorized_operations(username="testuser", password="testpass123"):
    """测试登录状态下的操作"""
    print("\n=== 测试授权操作 ===")
    
    # 登录用户
    print("\n1. 用户登录")
    assert login_user(username, password), "用户登录失败"
    print("✓ 登录测试通过")
    
    # 测试上传
    print("\n2. 测试登录状态上传歌曲")
    test_file = "D:\CloudMusic\Mameyudoufu - Somewhere Else.mp3"
    result = upload_song(test_file)
    assert result is not None, "登录状态应该能上传歌曲"
    print("✓ 登录状态上传测试通过")
    
    # 获取上传的歌曲ID
    song_id = result.get('id', 1)
    
    # 测试下载
    print("\n3. 测试登录状态下载歌曲")
    save_path = "downloads"
    result = download_song(song_id, save_path)
    assert result is not None, "登录状态应该能下载歌曲"
    print("✓ 登录状态下载测试通过")
    
    # 登出
    print("\n4. 用户登出")
    assert logout_user(), "用户登出失败"
    print("✓ 登出测试通过")

def run_all_tests():
    """运行所有测试"""
    try:
        print("=== 开始运行所有测试 ===")
        
        # 创建测试文件目录
        os.makedirs("test_files", exist_ok=True)
        os.makedirs("downloads", exist_ok=True)
        
        # 1. 测试未授权操作
        test_unauthorized_operations()
        
        # 2. 注册新用户（使用随机用户名）
        username = generate_random_username()
        password = "testpass123"
        if register_user(username, password, "test@example.com"):
            # 3. 测试授权操作
            test_authorized_operations(username, password)
        
        print("\n=== 所有测试完成 ===")
        
    except AssertionError as e:
        print(f"\n❌ 测试失败: {str(e)}")
    except Exception as e:
        print(f"\n❌ 测试过程中出现错误: {str(e)}")

if __name__ == "__main__":
    run_all_tests()

