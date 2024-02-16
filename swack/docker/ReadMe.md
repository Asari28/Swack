# AWS EC2インスタンスを立ち上げ
- ubuntu
- 鍵を作成し、秘密鍵をローカルにダウンロードしている
  - swack-key.pem
- セキュリティグループで22と5555を外部接続OKにしている


# EC2インスタンスにコンソールから接続
- インスタンスを選択し『接続』→『インスタンスに接続』の情報を使用する
```
ssh -i swack-key.pem ubuntu@<<AWS EC2のパブリック IPv4 アドレス>>
```

# Dockerのインストール
-  https://docs.docker.com/engine/install/ubuntu/

```
sudo apt-get update
sudo apt-get install ca-certificates curl gnupg
sudo install -m 0755 -d /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
sudo chmod a+r /etc/apt/keyrings/docker.gpg

echo \
  "deb [arch="$(dpkg --print-architecture)" signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
  "$(. /etc/os-release && echo "$VERSION_CODENAME")" stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt-get update

sudo apt-get -y install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
```

## Docker動作確認

```
sudo docker run hello-world
```

# ファイルのアップロード
## 事前準備
- 『docker』フォルダ内に最新の『swack.war』を配置する
	- パッケージ・エクスプローラーでプロジェクト右クリック→実行→Maven ビルド<br>
	ゴール：clean package antrun:run@copy-jar -DskipTests=true
- 『docker』フォルダをzipにする
	- エクスプローラで『docker』フォルダ右クリック→送る→zip

## ファイルアップロードおよび解凍

```
exit
scp -i swack-key.pem docker.zip ubuntu@<<AWS EC2のパブリック IPv4 アドレス>>:/home/ubuntu

ssh -i swack-key.pem ubuntu@<<AWS EC2のパブリック IPv4 アドレス>>
sudo apt install unzip
unzip docker.zip
```

## コンテナ起動
```
cd docker
sudo docker compose up -d --build
```

# ブラウザからアクセス
- http://<<AWS EC2のパブリック IPv4 アドレス>>:5555/swack/

※確認が終了したらEC2インスタンスを停止すること※