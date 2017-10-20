using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class Playingfield : MonoBehaviour
{
    public GameObject PlayerPrefab;
    public GameObject EnemyPrefab;
    public GameObject Block;
    public int TilesAmountWidth;
    public int TilesAmountHeight;
    public int EnemyAmount;
    public Text WinMessage;

    private GameObject _player;
    private List<GameObject> _enemies = new List<GameObject>();
    private int _moveCount = 0;


    void Start()
    {
        WinMessage.enabled = false;
        CreatePlayingfield();
        _player = PlaceActorRandomly(PlayerPrefab);

        for (int i = 0; i < EnemyAmount; i++)
        {
            _enemies.Add(PlaceActorRandomly(EnemyPrefab));
        }
    }

    void Update()
    {
        if (Input.GetKeyDown(KeyCode.UpArrow))
            MovePlayer(0, 1);
        else if (Input.GetKeyDown(KeyCode.DownArrow))
            MovePlayer(0, -1);
        else if (Input.GetKeyDown(KeyCode.LeftArrow))
            MovePlayer(-1, 0);
        else if (Input.GetKeyDown(KeyCode.RightArrow))
            MovePlayer(1, 0);
    }

    public void CreatePlayingfield()
    {
        for (int i = 0; i < TilesAmountHeight; i++)
        {
            for (int j = 0; j < TilesAmountWidth; j++)
            {
                var position = new Vector3(j, 0, i);
                Instantiate(Block, position, Quaternion.identity);
            }
        }
    }

    public GameObject PlaceActorRandomly(GameObject prefab)
    {
        int x;
        int z;
        do
        {
            x = Random.Range(0, TilesAmountWidth);
            z = Random.Range(0, TilesAmountHeight);
        } while (GetOccupant(x, z) != null);

        var position = new Vector3(x, 1, z);
        return Instantiate(prefab, position, Quaternion.identity);
    }

    private GameObject GetOccupant(float x, float z)
    {
        if (_player == null)
            return null;

        var playerPos = _player.transform.localPosition;
        if (playerPos.x == x && playerPos.z == z)
            return _player;

        foreach (var enemy in _enemies)
        {
            var enemyPos = enemy.transform.localPosition;
            if (enemyPos.x == x && enemyPos.z == z)
                return enemy;
        }

        return null;
    }

    public void MovePlayer(int x, int z)
    {
        _moveCount++;
        var currentPosition = _player.transform.localPosition;
        var nextX = currentPosition.x + x;
        var nextZ = currentPosition.z + z;
        nextX = Mathf.Clamp(nextX, 0, TilesAmountWidth - 1);
        nextZ = Mathf.Clamp(nextZ, 0, TilesAmountHeight - 1);
        GameObject occupant = GetOccupant(nextX, nextZ);
        if (occupant == null || occupant == _player)
        {
            var nextPosition = new Vector3(nextX, 1, nextZ);
            _player.transform.localPosition = nextPosition;
        }
        else
        {
            _enemies.Remove(occupant);
            Destroy(occupant);
            if (_enemies.Count == 0) ShowWinMessage();
        }

	}

	private void ShowWinMessage()
	{
        WinMessage.enabled = true;
        WinMessage.text = "You win with " + _moveCount.ToString() + " moves";
	}
}