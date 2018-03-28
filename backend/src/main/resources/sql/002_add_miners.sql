BEGIN TRANSACTION;

INSERT INTO "miners"
VALUES (1, "dstm's ZCash / Equihash Nvidia Miner", "./zm/zm.exe",
        "zm --server <server> --port <port> --user <username/wallet>.<name>","0.6"),
        (2, "EWBF's CUDA Zcash miner", "./ewbf/miner.exe",
         "miner --server <server> --user <username/wallet>.<name> --pass <pass> --port <port>","0.3.4b");

END TRANSACTION;